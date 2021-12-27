package com.qianbo.msg.service;

import com.qianbo.mapper.UserSessionMapper;
import com.qianbo.msg.domain.UserSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.System.currentTimeMillis;

/**
 * @author zhumo 黔博科技
 * @date 2021/9/3 14:41
 */
@Service
@AllArgsConstructor
public class UserSessionService {

    private final UserSessionMapper userSessionMapper;

    public Long saveSession(Long userId, Long acceptId) {
        UserSession userSession = new UserSession().setUserId(userId).setAcceptId(acceptId).setCreateTime(currentTimeMillis());
        userSessionMapper.insert(userSession);
        return userSession.sessionId;
    }

    public Long userSession(Long userId, Long acceptId) {
        return userSessionMapper.userSession(userId, acceptId);
    }
}
