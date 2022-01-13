package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "OrderClient")
public interface OrderClient {



}
