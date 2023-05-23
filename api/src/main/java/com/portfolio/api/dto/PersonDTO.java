package com.portfolio.api.dto;

public class PersonDTO {

  private static final long serialVersionUID = 07L;

  private Long id;
  private String name;
  private String title;
  private String email;
  // not returning password  
  // private String password;
  private String location;
  private String aboutMe;
  private String imgUrl;
  private String imgBackUrl;
  private String webUrl;

  public PersonDTO() {

  }

  public PersonDTO(Long id, String name, String title, String email, String location,
      String aboutMe, String imgUrl, String imgBackUrl, String webUrl) {

    this.id = id;
    this.name = name;
    this.title = title;
    this.email = email;
    this.location = location;
    this.aboutMe = aboutMe;
    this.imgUrl = imgUrl;
    this.imgBackUrl = imgBackUrl;
    this.webUrl = webUrl;

  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getImgBackUrl() {
    return imgBackUrl;
  }

  public void setImgBackUrl(String imgBackUrl) {
    this.imgBackUrl = imgBackUrl;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

}
