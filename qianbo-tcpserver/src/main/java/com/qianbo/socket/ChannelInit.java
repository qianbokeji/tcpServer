package com.qianbo.socket;

import com.qianbo.socket.handler.AuthenticateHandler;
import com.qianbo.socket.handler.HeartBeatHandler;
import com.qianbo.socket.handler.LengthFieldHandler;
import com.qianbo.socket.handler.ServiceHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@AllArgsConstructor
public class ChannelInit extends ChannelInitializer<SocketChannel> {

    private final AuthenticateHandler authenticateHandler;

    private final HeartBeatHandler heartBeatHandler;

    private final ServiceHandler serviceHandler;

    private final LengthFieldHandler lengthFieldHandler;


    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        pipeline.addLast(new WebSocketServerProtocolHandler("/qianbo"));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(2048, 0, 2, 0, 2));
        pipeline.addLast(new IdleStateHandler(20, 0, 0));
        pipeline.addLast(heartBeatHandler);
        pipeline.addLast(authenticateHandler);

    }

//    @Override
//    public void initChannel(SocketChannel channel) {
//        channel.pipeline().addLast(
//                new LengthFieldBasedFrameDecoder(2048, 0, 2, 0, 2),
//                lengthFieldHandler, authenticateHandler,
//                new IdleStateHandler(61, 0, 0),
//                heartBeatHandler, serviceHandler);
//    }
}
