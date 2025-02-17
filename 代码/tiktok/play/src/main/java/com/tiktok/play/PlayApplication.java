package com.tiktok.play;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class PlayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlayApplication.class,args);
    }
}
