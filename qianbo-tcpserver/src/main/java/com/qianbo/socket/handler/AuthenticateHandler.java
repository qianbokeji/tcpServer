package com.qianbo.socket.handler;

import com.google.gson.Gson;
import com.qianbo.socket.TcpChannelGroup;
import com.qianbo.socket.dto.InformDTO;
import com.qianbo.socket.properties.CommonEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.qianbo.socket.properties.CommonEvent.sendMsg;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author zhumo 黔博科技
 * @date 2021/8/4 15:30
 * tcp登陆认证handler
 */
@Slf4j
@Component
@AllArgsConstructor
@ChannelHandler.Sharable
public class AuthenticateHandler extends ChannelInboundHandlerAdapter {

    private final TcpChannelGroup channelGroup;

    private static final String BEARER = "Bearer ";

    private final ServiceHandler serviceHandler;

    static final AttributeKey<Long> ID = AttributeKey.valueOf("userId");


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info(ctx.channel().remoteAddress().toString() + " is active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        TextWebSocketFrame frame = (TextWebSocketFrame) msg;
        String message = new String(frame.text().getBytes(), UTF_8);
        if (message.startsWith(BEARER)) {//用户认证
            Long id = Long.valueOf(message.substring(BEARER.length()));
            Channel channel = ctx.channel();
            channel.attr(ID).set(id);
            System.err.println("登录用户：" + ctx.channel().attr(ID).get());
            channelGroup.add(id, channel);
            ctx.pipeline().remove(this);
            channelGroup.send(ctx.channel().attr(ID).get(), new Gson().toJson(InformDTO.builder().msgType(-1).msg(sendMsg).build()));
            log.info("用户id:{} 验证成功", id);
        } else serviceHandler.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        log.error(ctx.channel().remoteAddress() + " is closed :" + cause.getMessage());
    }

}
