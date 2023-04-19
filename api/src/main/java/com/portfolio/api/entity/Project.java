package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Entity
@Table(name = "person")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String location;

  @Lob
  private String aboutMe;

  @Column(name = "img_url", length = 2048)
  private String ImgUrl;
  @Column(name = "img_back_url", length = 2048)
  private String ImgBackUrl;
  
  private String title;

  @Temporal(TemporalType.DATE)
  private Date birdDate;

  public Project() {

  }

  public Project(Long id, String name, String email, String location, String aboutMe, String ImgUrl, String ImgBackUrl, String title) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.location = location;
    this.aboutMe = aboutMe;
    this.ImgUrl = ImgUrl;
    this.ImgBackUrl = ImgBackUrl;
    this.title = title;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getBirdDate() {
    return birdDate;
  }

  public void setBirdDate(Date birdDate) {
    this.birdDate = birdDate;
  }

}