package com.example.springboot_netty;

import com.example.springboot_netty.server.NettyServer_;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@SpringBootApplication
public class SpringbootNettyApplication implements CommandLineRunner {

    @Value("${netty.port}")
    private Integer port;

    @Value("${netty.url}")
    private String url;

    @Resource
    private NettyServer_ server;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNettyApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(url, port);
        System.out.println("run..." + url);
        server.start(address);
    }
}
