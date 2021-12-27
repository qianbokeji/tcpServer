package com.qianbo.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.netty.buffer.Unpooled.buffer;

/**
 * @author zhumo 黔博科技
 * @date 2021/8/4 15:30
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class LengthFieldHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        TextWebSocketFrame frame = (TextWebSocketFrame) msg;
        ByteBuf byteBuf = (ByteBuf) (Object) frame.text();
        int length = byteBuf.readableBytes();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        byteBuf.release();
        ctx.fireChannelRead(bytes);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        byte[] bytes = (byte[]) msg;
        int length = bytes.length;
        ByteBuf byteBuf = buffer(2 + length);
        byteBuf.writeShort(length);
        byteBuf.writeBytes(bytes);
        ctx.write(byteBuf, promise);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn(cause.getMessage());
    }

}
