package com.example.orderserver.Controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class OrderController {


    @Value("${pattern.dateformat}")
    private String dateformat;

    @GetMapping("/order")
    public String test(){
        System.out.println("dateformat ====> " + dateformat);
        return "Hello Nacos " + dateformat;
    }
}
