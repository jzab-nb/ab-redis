package xyz.jzab.abredis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import xyz.jzab.abredis.client.AbRedisClient;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Configuration
@Data
@ConfigurationProperties("abredis.client")
@ComponentScan
public class AbRedisConfig {
    private String host="127.0.0.1";
    private Integer port=6379;
    private String pwd="";

    @Bean
    public AbRedisClient abRedisClient(){
        return new AbRedisClient(host,port,pwd);
    }
}
