package com.portfolio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "skill")
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int level;

  @Column(name = "is_technical")
  private boolean isTechnical;

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

}
