package com.example.springboot_netty.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zhuzhaoman
 * @date 2020/7/6 0006 23:07
 * @description 描述
 */
public class WebSocketChannelHanlder extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 获取客户端内容
     * @param channelHandlerContext
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("读取客户端的内容：" + textWebSocketFrame.text());

        String msg = textWebSocketFrame.text();

        // 判断消息是否是心跳
        if (msg.equals("heard")) {
            TextWebSocketFrame result = new TextWebSocketFrame("heard");
            channelHandlerContext.writeAndFlush(result);
            return;
        }

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        TextWebSocketFrame result = new TextWebSocketFrame(date);
        channelHandlerContext.writeAndFlush(result);
    }

    /**
     * 连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接...");
    }

    /**
     * 断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开...");
    }
}
