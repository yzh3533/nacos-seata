package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "OrderClient")
public interface OrderClient {

    @GetMapping("/order/create")
    String create();

}
