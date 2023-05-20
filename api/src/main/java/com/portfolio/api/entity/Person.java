package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
// setting "email" to be unique so it can't be 2 persons with the same email,
// and creation or update fails if we try to use an existing email.
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Person implements Serializable {

  private static final long serialVersionUID = 01L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100)
  private String name;

  private String title;

  //class properties for log-in  
  private String email;
  private String password;

  private String location;

  @Column(length = 2048)
  private String aboutMe;

  @Column(name = "img_url", length = 2048)
  private String imgUrl;
  @Column(name = "img_back_url", length = 2048)
  private String imgBackUrl;
  @Column(name = "web_url", length = 2048)
  private String webUrl;

  public Person() {

  }

  public Person(String name, String title, String email, String password, String location, String aboutMe, String imgUrl, String imgBackUrl, String webUrl) {

    this.name = name;
    this.title = title;
    this.email = email;
    this.password = password;
    this.location = location;
    this.aboutMe = aboutMe;
    this.imgUrl = imgUrl;
    this.imgBackUrl = imgBackUrl;
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

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public void clearPassword() {
    this.password = "";
  }

}
