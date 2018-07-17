package com.chub.eurekaclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengtianping
 * @description
 * @date 2018/7/17
 */
@RestController
public class UserController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/client/user/get")
    public Result<String> get(@RequestParam String name){
        Result<String> result = new Result<>();
        // 演示 链路器的作用
        //int i = 1/0;
        result.setData("hello world == " + name + " === " + port);
        return result;
    }
}
