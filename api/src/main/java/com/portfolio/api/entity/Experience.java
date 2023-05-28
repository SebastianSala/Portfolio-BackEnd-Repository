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
@Table(name = "experience")
public class Experience implements Serializable {

  private static final long serialVersionUID = 05L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String position;

  @Column(length = 2048)
  private String description;

  private String company;

  @Temporal(TemporalType.DATE)
  @Column(columnDefinition = "DATE")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @Temporal(TemporalType.DATE)
  @Column(columnDefinition = "DATE")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @Column(name = "logo_url", length = 2048)
  private String logoUrl;
  @Column(name = "web_url", length = 2048)
  private String webUrl;

  // Many to one relationship with the person
  // @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // Using EAGER to actually retrieve the person on the query
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

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

}
