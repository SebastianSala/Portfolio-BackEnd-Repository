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
@Table(name = "skill")
public class Skill implements Serializable {

  private static final long serialVersionUID = 03L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int level;

  @Column(name = "is_technical")
  private boolean isTechnical;

  // Many to one relationship with the person
  // @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // Using EAGER to actually retrieve the person on the query
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "person_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  // JsonIgnore works in conjuction with FetchType.LAZY
  // @JsonIgnore
  private Person person;

  public Skill() {
  }

  public Skill(String name, int level, boolean isTechnical) {
    this.name = name;
    this.level = level;
    this.isTechnical = isTechnical;
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

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public boolean isIsTechnical() {
    return isTechnical;
  }

  public void setIsTechnical(boolean isTechnical) {
    this.isTechnical = isTechnical;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

}
