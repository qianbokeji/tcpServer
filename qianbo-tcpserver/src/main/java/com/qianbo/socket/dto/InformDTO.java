package com.qianbo.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhumo 黔博科技
 * @date 2021/9/3 0:11
 */
@AllArgsConstructor
@Data
@Builder
public class InformDTO {
    //发送者
    public Long userId;
    //发送者头像
    public String userHead;
    //接收者(通知发送者 返回消息ID)
    public Long acceptId;
    //接收者头像
    public String acceptHead;
    //消息类型 -1=事件通知 0=文字 1=图片 2=视频
    public Integer msgType;
    //内容
    public String msg;//{"userId":1,"userHead":"2","acceptId":2,"acceptHead":"1","msgType":"0","msg":"你好"}
    //会话ID
    public Long sessionId;
    //时间
    public Long createTime;
}
