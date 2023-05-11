package com.portfolio.api.service;

import com.portfolio.api.entity.Experience;
import com.portfolio.api.repository.ExperienceRepository;
import com.portfolio.api.service.interfaces.IExperienceService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class ExperienceService implements IExperienceService {

  @Autowired
  ExperienceRepository experienceRepository;

  @Override
  public void createExperience(Experience experience) {
    this.experienceRepository.save(experience);
  }

  @Override
  public void editExperience(Experience experience) {
    this.experienceRepository.save(experience);
  }

  @Override
  public ArrayList<Experience> listExperiences() {
    return (ArrayList<Experience>) this.experienceRepository.findAll();
  }

  @Override
  public Optional<Experience> findExperience(Long id) {
    return this.experienceRepository.findById(id);
  }

  @Override
  public void deleteExperience(Long id) {
    this.experienceRepository.deleteById(id);
  }

}
