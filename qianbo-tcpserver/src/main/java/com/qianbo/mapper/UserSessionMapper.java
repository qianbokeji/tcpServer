package com.qianbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianbo.msg.domain.UserSession;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhumo 黔博科技
 * @date 2021/9/3 14:41
 */
@Mapper
public interface UserSessionMapper extends BaseMapper<UserSession> {

    Long userSession(Long userId, Long acceptId);
}
