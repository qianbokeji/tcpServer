package com.qianbo.socket;

import com.qianbo.socket.properties.CommonProperties;
import com.qianbo.socket.properties.SocketProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.qianbo.socket.ChannelFactory.createEventLoopGroup;
import static com.qianbo.socket.ChannelFactory.getServerSocketChannel;
import static io.netty.buffer.PooledByteBufAllocator.DEFAULT;
import static io.netty.channel.ChannelOption.*;

/**
 * netty tcp服务依赖spring容器启动
 *
 * @author zhumo 黔博科技
 * @date 2021/8/4 15:29
 */
@Slf4j
@Service
@AllArgsConstructor
public class TcpServer implements CommandLineRunner {

    private final ChannelInit channelInit;

    private final CommonProperties commonProperties;

    @Async
    @Override
    public void run(String... args) {
        log.info("netty socketServer starting");
//        SocketProperties socket = commonProperties.getSocket();
        String strategy = "nio";/*socket.getStrategy();*/
        EventLoopGroup boss = createEventLoopGroup(strategy);
        EventLoopGroup worker = createEventLoopGroup(strategy, 8);
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(boss, worker)
                    .channel(getServerSocketChannel(strategy))
                    .option(SO_BACKLOG, 1024)
                    .option(ALLOCATOR, DEFAULT)
                    .childOption(SO_KEEPALIVE, true)
                    .childHandler(channelInit);
            int tcpPort = 9998/*socket.getTcpPort()*/;
            ChannelFuture future = bootstrap.bind(tcpPort).sync();
            log.info("netty socketServer started on port(s) " + tcpPort + " (tcp)");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
