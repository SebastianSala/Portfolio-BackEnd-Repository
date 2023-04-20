package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "network")
public class Network {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "logo_type", length = 45)
  private String logoType;

  @Column(name = "net_url", length = 2048)
  private String netUrl;

  public Network() {
  }

  public Network(Long id, String name, String logoType, String netUrl) {
    this.id = id;
    this.name = name;
    this.logoType = logoType;
    this.netUrl = netUrl;
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

  public String getLogoType() {
    return logoType;
  }

  public void setLogoType(String logoType) {
    this.logoType = logoType;
  }

  public String getNetUrl() {
    return netUrl;
  }

  public void setNetUrl(String netUrl) {
    this.netUrl = netUrl;
  }

}
