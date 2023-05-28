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
@Table(name = "education")
public class Education implements Serializable {

  private static final long serialVersionUID = 06L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String institution;

  @Column(length = 2048)
  private String description;

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

  public Education() {

  }

  public Education(String title, String institution, String description, LocalDate startDate, LocalDate endDate, String logoUrl, String webUrl) {
    this.title = title;
    this.institution = institution;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.logoUrl = logoUrl;
    this.webUrl = webUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getInstitution() {
    return institution;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
