package com.qianbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianbo.msg.domain.InformMsg;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhumo 黔博科技
 * @date 2021/9/3 2:15
 */
@Mapper
public interface InformMsgMapper extends BaseMapper<InformMsg> {

    IPage<InformMsg> selectAllByUserIdAndAcceptId(IPage<InformMsg> page, Long userId, Long acceptId);

    Long selectSessionId(Long userId, Long acceptId);


}
