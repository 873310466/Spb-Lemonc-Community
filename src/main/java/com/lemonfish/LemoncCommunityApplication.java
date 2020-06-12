package com.lemonfish;

import com.lemonfish.dto.AliyunOSSDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties({
        AliyunOSSDTO.class
})
public class LemoncCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemoncCommunityApplication.class, args);

    }

}
