package com.portfolio.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition
public class SwaggerConfig {

  @Bean
  public OpenAPI config() {

    return new OpenAPI()
        .info(
            new Info()
                .title("")
                .version("")
                .description("")
        );

  }

}
