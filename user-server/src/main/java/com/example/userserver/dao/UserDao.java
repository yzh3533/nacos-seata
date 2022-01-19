package com.example.userserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateAccount(BigDecimal price,Long id){
        String sql = "Update `user` set account = account - ? where id = ?;";
        jdbcTemplate.update(sql,price,id);
    }
}
