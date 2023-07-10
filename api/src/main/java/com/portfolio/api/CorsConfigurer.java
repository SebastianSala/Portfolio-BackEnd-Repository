package com.portfolio.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfigurer implements WebMvcConfigurer {

  @Value("${api.corsOrigins.local}")
  private String corsLocalUrl;
  @Value("${api.corsOrigins.remote}")
  private String corsRemoteUrl;

  @Override
  public void addCorsMappings(CorsRegistry corsRegistry) {
    corsRegistry.addMapping("/**")
        .allowedOrigins(this.corsLocalUrl, this.corsRemoteUrl)
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        //            .allowCredentials(true)
        .maxAge(3600);
  }

}
