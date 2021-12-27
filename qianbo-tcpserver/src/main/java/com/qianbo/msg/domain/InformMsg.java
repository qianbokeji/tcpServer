package com.qianbo.msg.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 聊天记录
 *
 * @author zhumo 黔博科技
 * @date 2021/9/3 2:13
 */
@TableName("inform_msg")
@Data
@Accessors(chain = true)
public class InformMsg {
    @TableId(type = IdType.AUTO)
    public Long msgId;
    //发送者
    public Long userId;
    //发送者头像
    public String userHead;
    //接收者
    public Long acceptId;
    //接收者头像
    public String acceptHead;
    //消息类型 0=文字 1=图片 2=视频
    public Integer msgType;
    //内容
    public String msg;
    //会话ID
    public Long sessionId;
    //时间
    public Long createTime;
}
