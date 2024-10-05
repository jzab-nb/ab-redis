package xyz.jzab.abredistest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.jzab.abredis.client.AbRedisClient;

import javax.annotation.Resource;

@SpringBootApplication
public class AbRedisTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(AbRedisTestApplication.class, args);
    }

}
