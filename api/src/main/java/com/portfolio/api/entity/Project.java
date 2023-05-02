package com.portfolio.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
//import java.time.LocalDate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "project")
public class Project implements Serializable {

  private static final long serialVersionUID = 02L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100)
  private String name;

  //using String instead of DATE or LocalDate types for compatibility.
  //JPA manages sure the format and type convertions.
//  private String date;
//  @Column(name = "date", columnDefinition = "DATE")
  @Temporal(TemporalType.DATE)
  private String date;
//  private LocalDate date;

  @Column(name = "short_description", length = 100)
  private String shortDescription;
  @Column(name = "long_description", length = 2048)
  private String longDescription;

  @Column(name = "logo_url", length = 2048)
  private String logoUrl;
  @Column(name = "img_url", length = 2048)
  private String imgUrl;
  @Column(name = "web_url", length = 2048)
  private String webUrl;

  //Many to one relationship with the person
//  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "person_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
//  @JsonIgnore
  private Person person;

//  public Project() {
//
//  }
//
//  public Project(String name, StringTime date, String shortDescription, String longDescription, String logoUrl, String imgUrl, String webUrl, Person person) {
//    this.name = name;
//    this.date = date;
//    this.shortDescription = shortDescription;
//    this.longDescription = longDescription;
//    this.logoUrl = logoUrl;
//    this.imgUrl = imgUrl;
//    this.webUrl = webUrl;
//    this.person = person;
//  }
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

//  public LocalDate getDate() {
  public String getDate() {
    return date;
  }

//  public void setDate(LocalDate date) {
  public void setDate(String date) {
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
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
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
