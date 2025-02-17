package com.tiktok.play.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisUniqueIdGenerator {
    private final StringRedisTemplate template;
    private final long BEGIN_TIMESTAMP=946684800L;
    private final int COUNT_BITS = 30;

    /**
     * id组成：符号位（1位）+时间戳（33位）+序列号（30位）
     * @param prefix 标识
     * @return id
     */
    public Long nextId(String prefix){
        //获取时间戳
        LocalDateTime now=LocalDateTime.now();
        long second = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = second - BEGIN_TIMESTAMP;
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        Long count = template.opsForValue().increment(prefix + ":" + date);
        if(count==null){
            log.error("Failed to generate unique ID. Increment operation returned null for key: {}"
                    , prefix + ":" + date);
            throw new RuntimeException("An error occurred while generating unique ID");
        }
        return timestamp << COUNT_BITS | count;
    }
}
