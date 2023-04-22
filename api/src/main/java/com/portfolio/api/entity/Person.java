package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100)
  private String name;

  private String title;
  private String email;
  private String location;

  @Column(length = 2048)
  private String aboutMe;

  @Column(name = "img_url", length = 2048)
  private String ImgUrl;
  @Column(name = "img_back_url", length = 2048)
  private String ImgBackUrl;
  @Column(name = "web_url", length = 2048)
  private String webUrl;

  public Person() {

  }

  public Person(String name, String title, String email, String location, String aboutMe, String ImgUrl, String ImgBackUrl, String webUrl) {
    this.name = name;
    this.title = title;
    this.email = email;
    this.location = location;
    this.aboutMe = aboutMe;
    this.ImgUrl = ImgUrl;
    this.ImgBackUrl = ImgBackUrl;
    this.webUrl = webUrl;
  }

  public Long getId() {
    return id;
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
    return ImgUrl;
  }

  public void setImgUrl(String ImgUrl) {
    this.ImgUrl = ImgUrl;
  }

  public String getImgBackUrl() {
    return ImgBackUrl;
  }

  public void setImgBackUrl(String ImgBackUrl) {
    this.ImgBackUrl = ImgBackUrl;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

}
