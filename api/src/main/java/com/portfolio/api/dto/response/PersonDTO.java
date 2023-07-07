package com.portfolio.api.dto.response;

import com.portfolio.api.entity.Person;
import java.util.List;
import java.util.stream.Collectors;
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

  public PersonDTO(Person person) {
    this.id = person.getId();
    this.name = person.getName();
    this.title = person.getTitle();
    this.email = person.getEmail();
    this.location = person.getLocation();
    this.aboutMe = person.getAboutMe();
    this.imgUrl = person.getImgUrl();
    this.imgBackUrl = person.getImgBackUrl();
    this.webUrl = person.getWebUrl();

    this.roles = person.getRoles()
        .stream()
        .map(role -> (role.getName().name()))
        .collect(Collectors.toList());
  }

}
