package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "order-client",url = "localhost:8099")
public interface OrderClient {

    @PostMapping("/order/create")
    void create();

    @PostMapping("/order/update")
    void update();
}
