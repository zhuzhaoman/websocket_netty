package com.example.springboot_netty.controller;

import com.example.springboot_netty.client.ClientServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuzhaoman
 * @date 2020/7/6 0006 15:32
 * @description 描述
 */
@RestController
public class NettyController {

    @Resource
    private ClientServer clientServer;

    @GetMapping("/test")
    public Object test(String title) throws InterruptedException {

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);

        ClientServer.sendMessage(title);

        return result;
    }
}
