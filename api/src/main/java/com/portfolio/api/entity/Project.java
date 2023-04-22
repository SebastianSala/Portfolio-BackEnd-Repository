package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;

@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100)
  private String name;

  @Temporal(TemporalType.DATE)
  private LocalDate date;

  @Column(name = "short_description", length = 100)
  private String shortDescription;
  @Column(name = "long_description", length = 2048)
  private String longDescription;

  @Column(name = "logo_url", length = 2048)
  private String logoUrl;
  @Column(name = "img_url", length = 2048)
  private String ImgUrl;
  @Column(name = "web_url", length = 2048)
  private String webUrl;

  //Many to one relationship with the person
  @ManyToOne
  @JoinColumn(name = "person_id", nullable = false)
  private Person person;

  public Project() {

  }

  public Project(String name, LocalDate date, String shortDescription, String longDescription, String logoUrl, String ImgUrl, String webUrl, Person person) {
    this.name = name;
    this.date = date;
    this.shortDescription = shortDescription;
    this.longDescription = longDescription;
    this.logoUrl = logoUrl;
    this.ImgUrl = ImgUrl;
    this.webUrl = webUrl;
    this.person = person;
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

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getImgUrl() {
    return ImgUrl;
  }

  public void setImgUrl(String ImgUrl) {
    this.ImgUrl = ImgUrl;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  public Person getPerson() {
    return this.person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

}
