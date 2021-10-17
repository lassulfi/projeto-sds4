package com.devsuperior.dsvendas.config;

import com.devsuperior.dsvendas.dtos.NoResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(new ApiInfoBuilder()
                        .title("DS Vendas")
                        .description("Projeto desenvolvido na Semana Spring-React da DevSuperior. API do backend para cadastro de vendedores, vendas e relatórios gerenciais.")
                        .version("1.0")
                        .build())
                    .useDefaultResponseMessages(false)
                    .directModelSubstitute(NoResponse.class, Void.class)
                    .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                    .paths(PathSelectors.any())
                    .build();
    }
}
