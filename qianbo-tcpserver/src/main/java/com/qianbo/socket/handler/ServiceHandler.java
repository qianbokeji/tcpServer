package com.qianbo.socket.handler;

import com.google.gson.Gson;
import com.qianbo.msg.service.InformMsgService;
import com.qianbo.msg.service.UserSessionService;
import com.qianbo.socket.TcpChannelGroup;
import com.qianbo.socket.dto.InformDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Component;

import static com.qianbo.socket.handler.AuthenticateHandler.ID;
import static com.qianbo.socket.properties.CommonEvent.Login;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * @author zhumo 黔博科技
 * @date 2021/8/4 15:43
 */
@Slf4j
@Component
@AllArgsConstructor
//@ChannelHandler.Sharable
public class ServiceHandler extends ChannelInboundHandlerAdapter {

    private final TcpChannelGroup channelGroup;

    private final InformMsgService informMsgService;

    private final UserSessionService userSessionService;

    private final AsyncListenableTaskExecutor asyncExecutor;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        Long userId = ctx.channel().attr(ID).get();

        TextWebSocketFrame frame = (TextWebSocketFrame) msg;

        String message = new String(frame.text().getBytes(), UTF_8);

        asyncExecutor.execute(() -> {

            InformDTO informDTO = new Gson().fromJson(message, InformDTO.class);

            Long session = checkSession(informDTO.userId, informDTO.acceptId);

            informDTO.setSessionId(session);

            informDTO.setCreateTime(currentTimeMillis());

            channelGroup.send(informDTO.acceptId, new Gson().toJson(informDTO));

            Long msgId = informMsgService.saveMsg(informDTO);

            channelGroup.send(informDTO.userId, new Gson().toJson(InformDTO.builder().msg(Login).sessionId(informDTO.sessionId).acceptId(msgId).build()));

        });

        log.info("收到用户:{},消息:{}", userId, message);
    }


    public Long checkSession(Long userId, Long acceptId) {

        Long userSession = userSessionService.userSession(userId, acceptId);

        if (userSession == null) userSession = userSessionService.saveSession(userId, acceptId);

        return userSession;
    }

}
