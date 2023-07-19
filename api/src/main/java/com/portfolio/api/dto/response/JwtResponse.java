package com.portfolio.api.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

  private String accessToken;
  private String tokenType = "Bearer";
  private Long id;
  private String name;
  private String title;
  private String email;
  private String location;
  private String aboutMe;
  private String imgUrl;
  private String imgBackUrl;
  private String webUrl;
  private List<String> roles;

  public JwtResponse(
      String accessToken,
      Long id,
      String name,
      String title,
      String email,
      String location,
      String aboutMe,
      String imgUrl,
      String imgBackUrl,
      String webUrl,
      List<String> roles
  ) {
    this.accessToken = accessToken;
    this.id = id;
    this.name = name;
    this.name = title;
    this.email = email;
    this.email = location;
    this.email = aboutMe;
    this.email = imgUrl;
    this.email = imgBackUrl;
    this.email = webUrl;
    this.roles = roles;
  }

}
