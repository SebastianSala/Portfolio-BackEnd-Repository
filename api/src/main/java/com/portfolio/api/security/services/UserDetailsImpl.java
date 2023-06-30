package com.portfolio.api.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.api.entity.Person;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 11L;

  private Long id;
  private String name;
  private String title;
  private String email;

  @JsonIgnore
  private String password;

  private String location;
  private String aboutMe;
  private String imgUrl;
  private String imgBackUrl;
  private String webUrl;

  private Collection<? extends GrantedAuthority> grantedAuthority;

  public UserDetailsImpl(Long id, String name, String title, String email, String password,
      String location, String aboutMe, String imgUrl, String imgBackUrl, String webUrl,
      Collection<? extends GrantedAuthority> grantedAuthority) {

    this.id = id;
    this.name = name;
    this.title = title;
    this.email = email;
    this.password = password;
    this.location = location;
    this.aboutMe = aboutMe;
    this.imgUrl = imgUrl;
    this.imgBackUrl = imgBackUrl;
    this.webUrl = webUrl;
    this.grantedAuthority = grantedAuthority;

  }

  public static UserDetailsImpl build(Person person) {
    List<GrantedAuthority> authorities = person.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        person.getId(),
        person.getName(),
        person.getTitle(),
        person.getEmail(),
        person.getPassword(),
        person.getLocation(),
        person.getAboutMe(),
        person.getImgUrl(),
        person.getImgBackUrl(),
        person.getWebUrl(),
        authorities
    );
  }

  // All the Overrides
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.grantedAuthority;
  }

  @Override
  public String getUsername() {
    return this.name;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object theObject) {
    if (this == theObject) {
      return true;
    }

    if (theObject == null || getClass() != theObject.getClass()) {
      return false;
    }

    UserDetailsImpl person = (UserDetailsImpl) theObject;
    return Objects.equals(this.id, person.id);

  }

  // the rest of the person getters
  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getEmail() {
    return email;
  }

  public String getLocation() {
    return location;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public String getImgBackUrl() {
    return imgBackUrl;
  }

  public String getWebUrl() {
    return webUrl;
  }

}
