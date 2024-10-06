package xyz.jzab.abredistest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jzab.abredis.client.AbRedisClient;

import javax.annotation.Resource;

@SpringBootTest
class AbRedisTestApplicationTests {
    @Resource
    AbRedisClient abRedisClient;
    @Test
    void contextLoads() {
        abRedisClient.info();
        System.out.println(abRedisClient.ping( ));
        System.out.println(abRedisClient.set("jzab", "jzab"));
        System.out.println(abRedisClient.get("jzab"));
    }

}
