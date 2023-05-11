package com.portfolio.api.service;

import com.portfolio.api.entity.Skill;
import com.portfolio.api.repository.SkillRepository;
import com.portfolio.api.service.interfaces.ISkillService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class SkillService implements ISkillService {

  @Autowired
  SkillRepository skillRepository;

  @Override
  public void createSkill(Skill skill) {
    skillRepository.save(skill);
  }

  @Override
  public void editSkill(Skill skill) {
    skillRepository.save(skill);
  }

  @Override
  public ArrayList<Skill> listSkills() {
    return (ArrayList<Skill>) skillRepository.findAll();
  }

  @Override
  public Optional<Skill> findSkill(Long Id) {
    return skillRepository.findById(Id);
  }

  @Override
  public void deleteSkill(Long id) {
    skillRepository.deleteById(id);
  }

}
