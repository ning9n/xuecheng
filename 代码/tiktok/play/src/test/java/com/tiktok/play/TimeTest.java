package com.tiktok.play;

import org.junit.jupiter.api.Test;

import java.time.*;


public class TimeTest {
    @Test
    public void getBeginTimeStamp(){
        //指定时间
        LocalDateTime localDateTime = LocalDateTime.of(2000, 1, 1, 0, 0);
        long second = localDateTime.toEpochSecond(ZoneOffset.UTC);
        System.out.println(second);
    }

}
