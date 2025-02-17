package com.tiktok.media.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfigurationProperties {
    private String bucket;
    private String endpoint;
    private String accessKey;
    private String secretKey;

}
