package com.qianbo.socket.comm.rabbit;

import com.google.gson.Gson;
import com.qianbo.socket.TcpChannelGroup;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhumo 黔博科技
 * @date 2021/8/4 20:45
 */
@Slf4j
@Component
@AllArgsConstructor
public class MessageListener {

    //交换机
    private static final String INFORM = "INFORM";

    //评论、点赞
    public static final String COMMUNITY_LIKE = "COMMUNITY_LIKE";

    //开团通知
    public static final String TeamOpenNotice = "TEAM_OPEN_NOTICE";

    //充值
    public static final String USER_CHARGE = "USER_CHARGE";

    //反馈处理通知
    public static final String FEEDBACK_NOTICE = "FEEDBACK_NOTICE";

    //实名认证审核通知
    public static final String AUDIT_AUTH_NOTICE = "AUDIT_AUTH_NOTICE";

    //加入拼团通知
    public static final String JOIN_TEAM_NOTICE = "JOIN_TEAM_NOTICE";

    //订单处理通知
    public static final String ORDER_APPLY_NOTICE = "ORDER_APPLY_NOTICE";

    private final TcpChannelGroup channelGroup;

    //订单处理通知
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(INFORM), value = @Queue(ORDER_APPLY_NOTICE), key = ORDER_APPLY_NOTICE))
    public void ORDER_APPLY_NOTICE(@Payload String body) {
        log.info("==========订单处理通知=========");
        msgDTO(body);

    }

    //加入拼团通知
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(INFORM), value = @Queue(JOIN_TEAM_NOTICE), key = JOIN_TEAM_NOTICE))
    public void JOIN_TEAM_NOTICE(@Payload String body) {
        log.info("==========加入拼团通知=========");
        msgDTO(body);

    }

    //社区点赞通知
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(INFORM), value = @Queue(COMMUNITY_LIKE), key = COMMUNITY_LIKE))
    public void COMMUNITY_LIKE(@Payload String body) {
        log.info("==========社区点赞通知=========");
        msgDTO(body);

    }

    //开团通知
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(INFORM), value = @Queue(TeamOpenNotice), key = TeamOpenNotice))
    public void TeamOpenNotice(@Payload String body) {
        log.info("==========开团通知=========");
        msgDTO(body);
    }

    //充值通知
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(INFORM), value = @Queue(USER_CHARGE), key = USER_CHARGE))
    public void USER_CHARGE(@Payload String body) {
        log.info("==========充值通知=========");
        msgDTO(body);
    }

    //充值通知
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(INFORM), value = @Queue(FEEDBACK_NOTICE), key = FEEDBACK_NOTICE))
    public void FEEDBACK_NOTICE(@Payload String body) {
        log.info("==========反馈处理通知=========");
        msgDTO(body);
    }

    //充值通知
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(INFORM), value = @Queue(AUDIT_AUTH_NOTICE), key = AUDIT_AUTH_NOTICE))
    public void AUDIT_AUTH_NOTICE(@Payload String body) {
        log.info("==========实名认证审核通知=========");
        msgDTO(body);
    }

    private void msgDTO(@Payload String body) {

        MsgDTO fromJson = new Gson().fromJson(body, MsgDTO.class);

        System.err.println("所有加入人员：" + fromJson.userId);

        for (Long userId : fromJson.userId) {

            System.err.println("发送ID:" + userId);

            Channel channel = channelGroup.find(userId);

            if (channel == null) {
                System.err.println(userId + ":未发送成功");
                continue;
            }

            channelGroup.send(userId, body);
        }
    }


    @Setter
    public static class MsgDTO {

        public List<Long> userId;

        public String param;

        //通知类型  0点赞 1社区评论 2 商品 3活动 4开团 5=反馈处理通知 6=加入拼团通知
        public Integer noticeType;

        public String msg;

        public Long currTime;

    }


}
