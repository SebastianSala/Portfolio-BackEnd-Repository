package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "network")
public class Network implements Serializable {

  private static final long serialVersionUID = 04L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100)
  private String name;

  @Column(name = "net", length = 2048)
  private String netUrl;

  // Many to one relationship with the person
  // @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // Using EAGER to actually retrieve the person
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "person_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  // JsonIgnore works in conjuction with FetchType.LAZY
  // @JsonIgnore
  private Person person;

  public Network() {
  }

  public Network(String name, String logoType, String netUrl) {
    this.name = name;
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

  public String getNetUrl() {
    return netUrl;
  }

  public void setNetUrl(String netUrl) {
    this.netUrl = netUrl;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

}
