package com.example.orderserver.controller;

import com.example.orderserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class OrderController {

//    @Value("${pattern.dateformat}")
//    private String dateformat;
    @Autowired
    private OrderService orderService;

    @GetMapping("/order/create")
    public String create(){
        orderService.createOrder(1L,1L);
        return "create order success ";
    }
}
