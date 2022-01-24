package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "order-client",url = "localhost:8099")
public interface OrderClient {

    @GetMapping("/order/create")
    void create();

}
