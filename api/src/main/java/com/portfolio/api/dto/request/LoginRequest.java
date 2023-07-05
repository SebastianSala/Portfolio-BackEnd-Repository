package com.portfolio.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  @NotBlank
//  private String username;
  private String email;
  @NotBlank
  String password;

}
