package com.dp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties("minio")
public class MinioConfigurationProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
