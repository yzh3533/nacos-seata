package com.example.orderserver.service;

import com.example.orderserver.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;

    public void createOrder(Long userId,Long num){
        dao.createOrder(userId, num);
    }
}
