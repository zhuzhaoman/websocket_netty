package com.example.springboot_netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.net.InetSocketAddress;

/**
 * 自定义netty服务类
 */
@Component
@Slf4j
public class NettyServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture startServer(InetSocketAddress address) {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerChannelInitializer())
                    // 允许的最大连接数
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE);
            channelFuture = serverBootstrap.bind(address).sync();
            channel = channelFuture.channel();
        } catch (Exception e) {
            log.error("netty启动出错！",e);
        } finally {
            if (channelFuture != null && channelFuture.isSuccess()) {
                log.info("netty正在监听IP:" + address.getHostName() + " 端口:" + address.getPort() + ", 等待连接中...");
            } else {
                log.error("netty启动失败！");
            }
        }
        return channelFuture;
    }

    /**
     * 关闭netty服务
     */
    public void closeServer() {
        log.info("关闭netty服务。。。");
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("关闭netty服务成功！");
    }
}

