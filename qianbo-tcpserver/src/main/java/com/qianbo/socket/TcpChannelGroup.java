package com.qianbo.socket;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author zhumo 黔博科技
 * @date 2021/8/4 15:30
 */
@Component
public class TcpChannelGroup {

    private final Map<Object, Channel> channelMap;

    public TcpChannelGroup() {
        this.channelMap = new ConcurrentHashMap<>();
    }

    public boolean send(Object channelKey, String message) {
        Objects.requireNonNull(channelKey);
        Objects.requireNonNull(message);
        Channel channel = channelMap.get(channelKey);
        boolean existed = channel != null;
        if (existed) channel.writeAndFlush(new TextWebSocketFrame(message));
        return existed;
    }

    public Channel find(Object channelKey) {
        Objects.requireNonNull(channelKey);
        return channelMap.get(channelKey);
    }

    public void add(Object channelKey, Channel channel) {
        Objects.requireNonNull(channelKey);
        Objects.requireNonNull(channel);
        Channel oldChannel = channelMap.put(channelKey, channel);
        if (oldChannel != null) oldChannel.close();
    }


    public void remove(Object channelKey) {
        if (channelKey != null)
            channelMap.remove(channelKey);
    }

    public Map<Object, Channel> getAll() {
        return channelMap;
    }

}
