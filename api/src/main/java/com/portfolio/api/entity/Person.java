package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
// setting "email" to be unique so it can't be 2 persons with the same email,
// and creation or update fails if we try to use an existing email.
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Person implements Serializable {

  private static final long serialVersionUID = 01L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(length = 100)
  private String name;

  private String title;

  //class properties for log-in  
  @NotBlank
  @Size(max = 45)
  private String email;
  @NotBlank
  @Size(min = 8, max = 100)
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

// @ManyToMany(fetch = FetchType.LAZY)
// using EAGER to prevent the hibernate sesion to close to early and causing issues
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "person_roles",
      joinColumns = @JoinColumn(name = "person_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public Person() {

  }

  public Person(Long id, String name, String title, String email, String password, String location, String aboutMe, String imgUrl, String imgBackUrl, String webUrl) {

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

  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getEmail() {
    return this.email;
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
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getAboutMe() {
    return this.aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public String getImgUrl() {
    return this.imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getImgBackUrl() {
    return this.imgBackUrl;
  }

  public void setImgBackUrl(String imgBackUrl) {
    this.imgBackUrl = imgBackUrl;
  }

  public String getWebUrl() {
    return this.webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  public void clearPassword() {
    this.password = "";
  }

  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}
