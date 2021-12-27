package com.qianbo.socket.handler;

import com.qianbo.socket.TcpChannelGroup;
import io.lettuce.core.api.sync.RedisCommands;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.qianbo.socket.handler.AuthenticateHandler.ID;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 心跳处理
 *
 * @author zhumo 黔博科技
 * @date 2021/8/4 15:30
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private final TcpChannelGroup channelGroup;

    public static final String PING = "PING";

    public static final String PONG = "$PONG";

    public RedisCommands<String, String> redisCommands;

    public static final byte[] PING_BYTES = PING.getBytes(UTF_8);

    public HeartBeatHandler(TcpChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        TextWebSocketFrame frame = (TextWebSocketFrame) msg;
        if (Arrays.equals(frame.text().getBytes(), PING_BYTES)) {
//            log.info("-> 收到 " + PING + ",响应 " + PONG);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(PONG));
        } else ctx.fireChannelRead(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {

                case READER_IDLE:
                    Long id = ctx.channel().attr(ID).get();
                    handlerHeart(ctx, id);
                    break;

                case WRITER_IDLE: //写空闲时 给客户端返回一个pong
                    ctx.writeAndFlush(PONG);
                    break;

            }
        } else ctx.fireUserEventTriggered(evt);
    }

    public void handlerHeart(ChannelHandlerContext ctx, Long id) {

        String userKey = redisCommands.get(String.valueOf(id));

        System.out.println("=======心跳缓存====" + userKey);

        if (userKey == null) redisCommands.set(String.valueOf(id), String.valueOf(1));

        else {

            int parseInt = Integer.parseInt(userKey) + 1;

            redisCommands.set(userKey, String.valueOf(parseInt));

            if (parseInt > 3) {

                redisCommands.del(userKey);

                channelGroup.remove(ctx.channel().attr(ID).get());

                ctx.close();
            }

        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Long id = ctx.channel().attr(ID).get();
        log.warn(id + ":" + cause.getMessage());
    }

}
