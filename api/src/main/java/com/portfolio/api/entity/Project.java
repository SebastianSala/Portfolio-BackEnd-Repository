package com.portfolio.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDate;
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

  @Column(columnDefinition = "DATE")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private LocalDate date;

  @Column(name = "short_description", length = 100)
  private String shortDescription;
  @Column(name = "long_description", length = 2048)
  private String longDescription;

  @Column(name = "img_url", length = 2048)
  private String imgUrl;
  @Column(name = "web_url", length = 2048)
  private String webUrl;

  // Many to one relationship with the person
  // @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // Using EAGER to actually retrieve the person
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "person_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  // JsonIgnore works in conjuction with FetchType.LAZY
  // @JsonIgnore
  private Person person;

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
//  public String getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
//  public void setDate(String date) {
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
