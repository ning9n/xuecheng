package com.xuecheng.gateway;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 *     系统管理启动类
 * </p>
 *
 * @Description:
 */

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
    @Bean
    public ApplicationRunner runner(NacosDiscoveryProperties properties) {
        return args -> {
            System.out.println("Nacos Service Name: " + properties.getService());
        };
    }
}