package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Skill;
import java.util.ArrayList;
import java.util.Optional;

public interface ISkillService {

  public void createSkill(Skill skill);

  public void editSkill(Skill skill);

  public ArrayList<Skill> listSkills();

  public Optional<Skill> findSkill(Long Id);

  public void deleteSkill(Long id);

}
