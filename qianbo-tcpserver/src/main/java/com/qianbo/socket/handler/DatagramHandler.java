//package com.qianbo.socket.handler;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.channel.socket.DatagramPacket;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//
///**
// * @author zhumo 黔博科技
// * @date 2021/8/4 15:43
// */
//@Slf4j
//@Component
//public class DatagramHandler extends ChannelInboundHandlerAdapter {
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        DatagramPacket packet = (DatagramPacket) msg;
//        log.info(packet.content().toString(UTF_8));
//        log.info(packet.sender().toString());
//    }
//
//}
