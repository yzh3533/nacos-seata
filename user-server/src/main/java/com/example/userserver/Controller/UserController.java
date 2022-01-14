package com.example.userserver.Controller;

import com.example.feignclient.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private OrderClient orderClient;

    @GetMapping("/user")
    public void createOrder(){
        orderClient.create();
    }
}
