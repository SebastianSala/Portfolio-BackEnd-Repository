package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Skill;
import java.util.List;

public interface ISkillService {

  public void create(Skill skill);

  public void edit(Skill skill);

  public List<Skill> listByPersonEmail(String personEmail);

  public List<Skill> listByPersonId(Long personId);

  public Skill findById(Long id);

  public Skill findByPersonIdBySkillId(Long personId, Long skillId);

  public boolean existsByPersonIdBySkillId(Long personId, Long skillId);

  public boolean existsById(Long id);

  public boolean existsByPersonId(Long personId);

  public void delete(Long id);

  public void deleteByPersonId(Long personId);

}
