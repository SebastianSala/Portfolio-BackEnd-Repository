package com.portfolio.api.service;

import com.portfolio.api.entity.Skill;
import com.portfolio.api.repository.SkillRepository;
import com.portfolio.api.service.interfaces.ISkillService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class SkillService implements ISkillService {

  @Autowired
  SkillRepository skillRepository;

  @Override
  public void create(Skill skill) {
    skillRepository.save(skill);
  }

  @Override
  public void edit(Skill skill) {
    skillRepository.save(skill);
  }

  @Override
  public Skill findById(Long id) {
    return skillRepository.findById(id).orElse(null);
  }

  @Override
  public List<Skill> listByPersonEmail(String personEmail) {
    return this.skillRepository.findByPersonEmail(personEmail);
  }

  @Override
  public List<Skill> listByPersonId(Long personId) {
    return skillRepository.findByPersonId(personId);
  }

  @Override
  public Skill findByPersonIdBySkillId(Long personId, Long skillId) {
    return skillRepository.findByPersonIdAndId(personId, skillId);
  }

  @Override
  public boolean existsById(Long id) {
    return this.skillRepository.existsById(id);
  }

  @Override
  public boolean existsByPersonIdBySkillId(Long personId, Long skillId) {
    return this.skillRepository.existsByPersonIdAndId(personId, skillId);
  }

  @Override
  public boolean existsByPersonId(Long personId) {
    return this.skillRepository.existsByPersonId(personId);
  }

  @Override
  public void delete(Long id) {
    skillRepository.deleteById(id);
  }

  @Override
  public void deleteByPersonId(Long personId) {
    this.skillRepository.deleteByPersonId(personId);
  }

}
