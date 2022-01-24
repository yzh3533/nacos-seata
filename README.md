这是一个简单的nacos-seata分布式事务解决方案demo
# Nacos

## [Nacos快速入门](https://nacos.io/zh-cn/docs/quick-start.html)

启动服务器

### Linux/Unix/Mac

启动命令(standalone代表着单机模式运行，非集群模式):

`sh startup.sh -m standalone`

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：

`bash startup.sh -m standalone`

### Windows

启动命令(standalone代表着单机模式运行，非集群模式):

`startup.cmd -m standalone`

## Spring Clound

### 启动配置管理

1. 添加依赖：

```plain
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>${latest.version}</version>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <version>${latest.version}</version>
</dependency>
```

注意：版本 [2.1.x.RELEASE](https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config) 对应的是 Spring Boot 2.1.x 版本。版本 [2.0.x.RELEASE](https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config) 对应的是 Spring Boot 2.0.x 版本，版本 [1.5.x.RELEASE](https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config) 对应的是 Spring Boot 1.5.x 版本。
更多版本对应关系参考：[版本说明 Wiki](https://github.com/spring-cloud-incubator/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)

1. 在 `bootstrap.yml` 中配置 Nacos server 的地址和应用名

```plain
spring:
  application:
    #默认为 spring.application.name的值
    name: user-server
  cloud:
      #Nacos Data Id:name-环境对应的profile.配置内容格式
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        file-extension: yml
```

说明：之所以需要配置 `spring.application.name` ，是因为它是构成 Nacos 配置管理 `dataId`字段的一部分。
在 Nacos Spring Cloud 中，`dataId` 的完整格式如下：

```plain
${prefix}-${spring.profiles.active}.${file-extension}
```

* `prefix` 默认为 `spring.application.name` 的值，也可以通过配置项 `spring.cloud.nacos.config.prefix`来配置。
* `spring.profiles.active` 即为当前环境对应的 profile，详情可以参考 [Spring Boot文档](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html#boot-features-profiles)。 注意：当 `spring.profiles.active` 为空时，对应的连接符 `-` 也将不存在，dataId 的拼接格式变成 `${prefix}.${file-extension}`
* `file-exetension` 为配置内容的数据格式，可以通过配置项 `spring.cloud.nacos.config.file-extension` 来配置。目前只支持 `properties` 和 `yaml` 类型。
  ## 

# Seata

seata文档：

[seata快速入门](https://seata.io/zh-cn/docs/user/quickstart.html)
一些错误解决方案：

1.seata server程序启动的时候报错如下：

```plain
Caused by: java.sql.SQLException: Unknown system variable 'tx_read_only'
        at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:998) ~[na:na]
        at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3835) ~[na:na]
        at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3771) ~[na:na]
        at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:2435) ~[na:na]
        at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2582) ~[na:na]
        at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2531) ~[na:na]
        at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2489) ~[na:na]
        at com.mysql.jdbc.StatementImpl.executeQuery(StatementImpl.java:1446) ~[na:na]
        at com.mysql.jdbc.ConnectionImpl.isReadOnly(ConnectionImpl.java:3607) ~[na:na]
        ... 5 common frames omitted
```

mysql是8.0的，驱动类需要com.mysql.cj.jdbc.Driver，而不是com.mysql.jdbc.Driver。

```plain
Caused by: java.sql.SQLException: The server time zone value '?й??????' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the 'serverTimezone' configuration property) to use a more specifc time zone value if you want to utilize time zone support.
        at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:129)
        at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)
```

在数据库连接中增加时区配置
jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true

的后面增加&serverTimezone=GMT

```plain
jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true&serverTimezone=GMT
```

* spring-cloud-starter-alibaba-seata推荐依赖配置方式

```plain
           <dependency>
                <groupId>io.seata</groupId>
                <artifactId>seata-spring-boot-starter</artifactId>
                <version>最新版</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
                <version>2.2.1.RELEASE</version>
                <exclusions>
                    <exclusion>
                        <groupId>io.seata</groupId>
                        <artifactId>seata-spring-boot-starter</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
```

### 启动Server

Server端存储模式（store.mode）现有file、db、redis三种

注： file模式为单机模式，全局事务会话信息内存中读写并持久化本地文件root.data，性能较高;

db模式为高可用模式，全局事务会话信息通过db共享，相应性能差些;

redis模式Seata-Server 1.3及以上版本支持,性能较高,存在事务信息丢失风险,请提前配置合适当前场景的redis持久化配置.

### 

### 修改store.mode="db或者redis"

全局事务会话信息由3块内容构成，全局事务-->分支事务-->全局锁

可能出现的错误：ERR Client sent AUTH, but no password is set

若redis无密码删除store.db.password

对应表global_table、branch_table、lock_table（仅db模式）

