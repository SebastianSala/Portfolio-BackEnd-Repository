package com.portfolio.api.controller;

import com.portfolio.api.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class HealthCheckController {
  
  @GetMapping
  public ResponseEntity checkHealth() {
//    return new ResponseEntity(new Message("Backend working"), HttpStatus.OK);
    return new ResponseEntity("Backend working OK", HttpStatus.OK);
  }

}
