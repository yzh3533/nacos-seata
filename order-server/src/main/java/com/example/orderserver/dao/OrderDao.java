package com.example.orderserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createOrder(Long userId,Long num){
        String sql = "insert into `order` (user_id,num) values (?,?);";
        jdbcTemplate.update(sql,userId,num);
    }

    public void updateOrder(){
        String sql = "UPDATE `order` SET num = num + 1 WHERE id = 40;";
        jdbcTemplate.update(sql);
    }
}
