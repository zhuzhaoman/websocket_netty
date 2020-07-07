package com.example.springboot_netty;

import com.example.springboot_netty.server.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

@SpringBootApplication
public class SpringbootNettyApplication1 implements CommandLineRunner {

    @Value("${netty.url}")
    private String url;

    @Value("${netty.port}")
    private Integer port;

    @Resource
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNettyApplication1.class, args);
    }

    /**
     * 此处启动netty服务
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        InetSocketAddress address = new InetSocketAddress(url,port);

        // 启动netty服务器
        ChannelFuture channelFuture = nettyServer.startServer(address);

        // 钩子方法，关闭服务器
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                nettyServer.closeServer()
        ));

        channelFuture.channel().closeFuture().sync();
    }
}
