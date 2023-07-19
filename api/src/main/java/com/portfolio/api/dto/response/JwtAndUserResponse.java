package com.portfolio.api.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAndUserResponse {

  private String accessToken;
  private String tokenType = "Bearer";

  private PersonDTO personDTO;

}
