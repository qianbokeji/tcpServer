package com.qianbo.socket;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueueDatagramChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhumo 黔博科技
 * @date 2021/8/4 15:42
 */
public class ChannelFactory {

    public static EventLoopGroup createEventLoopGroup(String strategy) {
        return createEventLoopGroup(strategy, 0);
    }

    public static EventLoopGroup createEventLoopGroup(String strategy, int nThreads) {
        if (strategy == null) return null;
        switch (strategy) {
            case "nio":
                return new NioEventLoopGroup(nThreads);
            case "epoll":
                return new EpollEventLoopGroup(nThreads);
            case "kqueue":
                return new KQueueEventLoopGroup(nThreads);
            default:
                return null;
        }
    }

    public static Class<? extends ServerSocketChannel> getServerSocketChannel(String strategy) {
        if (strategy == null) return null;
        switch (strategy) {
            case "nio":
                return NioServerSocketChannel.class;
            case "epoll":
                return EpollServerSocketChannel.class;
            case "kqueue":
                return KQueueServerSocketChannel.class;
            default:
                return null;
        }
    }

    public static Class<? extends SocketChannel> getSocketChannel(String strategy) {
        if (strategy == null) return null;
        switch (strategy) {
            case "nio":
                return NioSocketChannel.class;
            case "epoll":
                return EpollSocketChannel.class;
            case "kqueue":
                return KQueueSocketChannel.class;
            default:
                return null;
        }
    }

    public static Class<? extends DatagramChannel> getDatagramChannel(String strategy) {
        if (strategy == null) return null;
        switch (strategy) {
            case "nio":
                return NioDatagramChannel.class;
            case "epoll":
                return EpollDatagramChannel.class;
            case "kqueue":
                return KQueueDatagramChannel.class;
            default:
                return null;
        }
    }
}
