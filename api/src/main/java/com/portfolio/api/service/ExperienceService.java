package com.portfolio.api.service;

import com.portfolio.api.entity.Experience;
import com.portfolio.api.repository.ExperienceRepository;
import com.portfolio.api.service.interfaces.IExperienceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class ExperienceService implements IExperienceService {

  @Autowired
  ExperienceRepository experienceRepository;

  @Override
  public void create(Experience experience) {
    experienceRepository.save(experience);
  }

  @Override
  public void edit(Experience experience) {
    experienceRepository.save(experience);
  }

  @Override
  public Experience findById(Long id) {
    return experienceRepository.findById(id).orElse(null);
  }

  @Override
  public List<Experience> listByPersonEmail(String personEmail) {
    return this.experienceRepository.findByPersonEmail(personEmail);
  }

  @Override
  public List<Experience> listByPersonId(Long personId) {
    return experienceRepository.findByPersonId(personId);
  }

  @Override
  public Experience findByPersonIdByExperienceId(Long personId, Long experienceId) {
    return experienceRepository.findByPersonIdAndId(personId, experienceId);
  }

  @Override
  public boolean existsById(Long id) {
    return this.experienceRepository.existsById(id);
  }

  @Override
  public boolean existsByPersonIdByExperienceId(Long personId, Long experienceId) {
    return this.experienceRepository.existsByPersonIdAndId(personId, experienceId);
  }

  @Override
  public boolean existsByPersonId(Long personId) {
    return this.experienceRepository.existsByPersonId(personId);
  }

  @Override
  public void delete(Long id) {
    experienceRepository.deleteById(id);
  }

  @Override
  public void deleteByPersonId(Long personId) {
    this.experienceRepository.deleteByPersonId(personId);
  }

}
