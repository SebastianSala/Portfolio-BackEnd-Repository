package com.portfolio.api.controller;

import com.portfolio.api.dto.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/")
//@CrossOrigin(origins = "*")
public class HealthCheckController {

  @GetMapping
  public ResponseEntity<String> checkHealth(HttpServletRequest httpServletRequest) {

    String swaggerUrl = ServletUriComponentsBuilder.fromRequest(httpServletRequest)
        .replacePath("/swagger-ui")
        .build().toUriString();

    String messageBackend = String.format("Backend OK, ingresa a <a href=\"%s\">%s</a> para ver la documentación de la API.", swaggerUrl, swaggerUrl);

    //String messageConsole = String.format("Backend OK, ingresa a %s para ver la documentación de la API.", swaggerUrl);
    //Message theMessage = new MessageResponse(messageConsole);
    //System.out.println("--- " + theMessage.getMessage());
    return new ResponseEntity<>(messageBackend, HttpStatus.OK);

  }

  @GetMapping("check")
  public ResponseEntity<?> checkHealthJSON(HttpServletRequest httpServletRequest) {

    String swaggerUrl = ServletUriComponentsBuilder.fromRequest(httpServletRequest)
        .replacePath("/swagger-ui")
        .build().toUriString();

    String message = String.format("Backend OK, ingresa a %s para ver la documentación de la API.", swaggerUrl);

    MessageResponse theMessage = new MessageResponse(message);

    System.out.println("--- (frontend check) " + theMessage.getMessage());

    return ResponseEntity.status(HttpStatus.OK).body(theMessage);

  }

}
