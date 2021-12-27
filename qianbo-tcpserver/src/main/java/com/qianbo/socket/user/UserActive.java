package com.qianbo.socket.user;

import com.qianbo.socket.TcpChannelGroup;
import com.qianbo.vo.ResponseVO;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhumo 黔博科技
 * @date 2021/8/31 9:37
 */
@RestController
@RequestMapping("UserActive")
@AllArgsConstructor
public class UserActive {

    private final TcpChannelGroup channelGroup;


    //用户活跃
    @GetMapping("GetUserActive")
    public ResponseVO<Integer> GetUserActive() {

        Map<Object, Channel> channelMap = channelGroup.getAll();

        int size = channelMap.size();

        return ResponseVO.success(size);
    }

}
