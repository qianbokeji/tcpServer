package com.qianbo.msg.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 会话
 *
 * @author zhumo 黔博科技
 * @date 2021/9/3 14:39
 */
@TableName("user_session")
@Data
@Accessors(chain = true)
public class UserSession {
    @TableId(type = IdType.AUTO)
    public Long sessionId;
    //发送者
    public Long userId;
    //接收者
    public Long acceptId;
    //时间
    public Long createTime;
}
