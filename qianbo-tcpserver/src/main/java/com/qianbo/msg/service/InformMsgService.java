package com.qianbo.msg.service;

import com.qianbo.mapper.InformMsgMapper;
import com.qianbo.msg.domain.InformMsg;
import com.qianbo.socket.dto.InformDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhumo 黔博科技
 * @date 2021/9/3 2:15
 */
@Service
@AllArgsConstructor
public class InformMsgService {

    private final InformMsgMapper informMsgMapper;

    @Transactional
    public Long saveMsg(InformDTO informDTO) {
        InformMsg informMsg = new InformMsg().setSessionId(informDTO.sessionId).setMsg(informDTO.msg).setMsgType(informDTO.msgType).setAcceptHead(informDTO.acceptHead).setAcceptId(informDTO.acceptId).setUserId(informDTO.userId).setUserHead(informDTO.userHead).setCreateTime(informDTO.createTime);
        informMsgMapper.insert(informMsg);
        return informMsg.msgId;
    }


}
