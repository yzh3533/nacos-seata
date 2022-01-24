package com.example.userserver.service;

import com.example.feignclient.OrderClient;
import com.example.userserver.dao.UserDao;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class UserService {

    @Autowired
    private OrderClient orderClient;
    @Autowired
    private UserDao dao;

    @GlobalTransactional(timeoutMills = 300000, name = "dubbo-gts-seata-example")
    public void updateAccount(BigDecimal price, Long id,Boolean flag){
        log.info("开始全局事务，XID = " + RootContext.getXID());
        orderClient.create();
        if(flag) {
            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
        }
        dao.updateAccount(price, id);
    }
}
