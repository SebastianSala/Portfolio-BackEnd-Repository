package com.portfolio.api.controller;

import com.portfolio.api.dto.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class HealthCheckController {

  @GetMapping
  public ResponseEntity<String> checkHealth(HttpServletRequest httpServletRequest) {

//    return new ResponseEntity("Backend working OK, go to '/swagger-ui' for api documantation", HttpStatus.OK);
    String swaggerUrl = ServletUriComponentsBuilder.fromRequest(httpServletRequest)
        .replacePath("/swagger-ui")
        .build().toUriString();

    String message = String.format("Backend OK, ingresa a <a href=\"%s\">%s</a> para ver la documentación de la API.", swaggerUrl, swaggerUrl);

    return new ResponseEntity<>(message, HttpStatus.OK);

  }

}
