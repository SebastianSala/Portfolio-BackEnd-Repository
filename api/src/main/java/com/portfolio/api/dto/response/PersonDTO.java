package com.portfolio.api.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

  private static final long serialVersionUID = 07L;

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

}
