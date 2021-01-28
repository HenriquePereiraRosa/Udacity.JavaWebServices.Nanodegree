package com.udacity.vehicles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
//@Primary
//@Qualifier
public class SwaggerConfig {
    @Bean
//    @Primary
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Vehicles REST API",
                "This API is a CRUD os Vehicles.",
                "1.0",
                "http://www.udacity.com/tos",
                new Contact("Henrique Rosa", "https://www.linkedin.com/in/henriquepereirarosa/", "henrique.prosa@server.com"),
                "License of API", "http://www.udacity.com/license", Collections.emptyList());
    }

}
