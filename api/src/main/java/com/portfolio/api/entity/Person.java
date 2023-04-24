package com.portfolio.api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
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
  private String ImgUrl;
  @Column(name = "img_back_url", length = 2048)
  private String ImgBackUrl;
  @Column(name = "web_url", length = 2048)
  private String webUrl;
  
  //Relatioships
//  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//  @JoinColumn(name = "person_id")
//  private List<Project> projects;

  public Person() {

  }

  public Person(String name, String title, String email, String password, String location, String aboutMe, String ImgUrl, String ImgBackUrl, String webUrl) {
    this.name = name;
    this.title = title;
    this.email = email;
    this.password = password;
    this.location = location;
    this.aboutMe = aboutMe;
    this.ImgUrl = ImgUrl;
    this.ImgBackUrl = ImgBackUrl;
    this.webUrl = webUrl;
    
    //this.projects = new ArrayList();
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
  
  //Relatioships getters and setters
//  public List<Project> getProjects() {
//    return this.projects;
//  }
//  
//  public void setProjects(List<Project> projects) {
//    this.projects = projects;
//  }

}
