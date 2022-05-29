package com.lomi.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    private boolean swaggerEnable;

    private ApiInfo apiInfo() {
        Contact contact = new Contact("TestExample", "", "");
        ApiInfo apiInfo = new ApiInfo(
                "TestExample",
                "TestExample",
                "V1.0.0",
                "",
                contact,
                "",
                "",
                new ArrayList<>());
        return apiInfo;
    }
    
    @Bean
    public Docket loginDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .groupName("LOGIN-API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lomi.controller"))
                .build();
    }


    public void setSwaggerEnable(boolean swaggerEnable) {
        this.swaggerEnable = true;
    }
    
}
