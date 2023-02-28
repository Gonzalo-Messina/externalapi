package com.externalapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI baseOpenApi(){
        return new OpenAPI()
                .info(new Info().title("External Api")
                        .version("1.0.0")
                        .description("This is an API that connect to a database and brings information of TV's"));
    }
}
