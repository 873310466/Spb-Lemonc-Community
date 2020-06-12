package com.lemonfish.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author Masics 张超
 * @date 2020/2/4 22:57
 */
@Configuration
@EnableSwagger2
@Profile({"dev","test"})
public class SwaggerConfig {

    //配置Swagger的Bean实例
    @Bean
    public Docket docket(Environment environment) {

        // 获取项目环境
        Profiles profile = Profiles.of("dev", "test");
        boolean flag = environment.acceptsProfiles(profile);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .groupName("张超")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lemonfish.controller"))
                //path() 过滤
//                .paths(PathSelectors.ant("/lemonfish/**"))
                .build();
    }


    //配置Swagger 信息=apiInfo
    private ApiInfo apiInfo() {
        Contact contact = new Contact("张超", "http://www.lemonfishzc.life", "873310466@qq.com");

        return new ApiInfo("柠檬鱼の日记",
                "冲就完事了噢~",
                "1.0",
                "http://localhost/lemonfish/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
