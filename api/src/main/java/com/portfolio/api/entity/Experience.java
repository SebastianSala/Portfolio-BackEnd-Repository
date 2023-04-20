package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;

@Entity
@Table(name = "experience")
public class Experience {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String position;

  @Column(length = 2048)
  private String description;

  private String company;

  @Temporal(TemporalType.DATE)
  @Column(name = "start_date")
  private LocalDate startDate;
  @Temporal(TemporalType.DATE)
  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "logo_url", length = 2048)
  private String logoUrl;
  @Column(name = "web_url", length = 2048)
  private String webUrl;

  public Experience() {

  }

  public Experience(Long id, String position, String description, String company, LocalDate startDate, LocalDate endDate, String logoUrl, String webUrl) {
    this.id = id;
    this.position = position;
    this.description = description;
    this.company = company;
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

}
