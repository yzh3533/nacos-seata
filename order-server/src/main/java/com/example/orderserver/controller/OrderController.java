package com.example.orderserver.controller;

import com.example.orderserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    public void create(){
        orderService.createOrder(1L,1L);
    }

    @PostMapping("/order/update")
    public void update(){
        orderService.updateOrder();
    }
}
