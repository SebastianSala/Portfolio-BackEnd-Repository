package com.portfolio.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

  @NotBlank
  @Size(min = 3, max = 45, message = "Longitud de Nombre incorrecta")
  private String username;

  @NotBlank
  @Size(max = 45, message = "Email inválido")
  @Email
  private String email;

  @NotBlank
  @Size(min = 8, max = 100, message = "Longitud del Password inválida")
  private String password;

  private Set<String> role;

}
