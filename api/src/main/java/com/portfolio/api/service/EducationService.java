package com.portfolio.api.service;

import com.portfolio.api.entity.Education;
import com.portfolio.api.repository.EducationRepository;
import com.portfolio.api.service.interfaces.IEducationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class EducationService implements IEducationService {

  @Autowired
  EducationRepository educationRepository;

  @Override
  public void create(Education education) {
    educationRepository.save(education);
  }

  @Override
  public void edit(Education education) {
    educationRepository.save(education);
  }

  @Override
  public Education findById(Long id) {
    return educationRepository.findById(id).orElse(null);
  }

  @Override
  public List<Education> listByPersonEmail(String personEmail) {
    return this.educationRepository.findByPersonEmail(personEmail);
  }

  @Override
  public List<Education> listByPersonId(Long personId) {
    return educationRepository.findByPersonId(personId);
  }

  @Override
  public Education findByPersonIdByEducationId(Long personId, Long educationId) {
    return educationRepository.findByPersonIdAndId(personId, educationId);
  }

  @Override
  public boolean existsById(Long id) {
    return this.educationRepository.existsById(id);
  }

  @Override
  public boolean existsByPersonIdByEducationId(Long personId, Long educationId) {
    return this.educationRepository.existsByPersonIdAndId(personId, educationId);
  }

  @Override
  public boolean existsByPersonId(Long personId) {
    return this.educationRepository.existsByPersonId(personId);
  }

  @Override
  public void delete(Long id) {
    educationRepository.deleteById(id);
  }

  @Override
  public void deleteByPersonId(Long personId) {
    this.educationRepository.deleteByPersonId(personId);
  }

}
