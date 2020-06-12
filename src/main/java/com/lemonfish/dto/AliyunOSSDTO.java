package com.lemonfish.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/12 18:04
 */
@Data
@ConfigurationProperties(prefix = "aliyun-oss")
public class AliyunOSSDTO {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
