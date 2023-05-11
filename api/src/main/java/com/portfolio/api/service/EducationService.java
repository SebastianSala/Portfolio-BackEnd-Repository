package com.portfolio.api.service;

import com.portfolio.api.entity.Education;
import com.portfolio.api.repository.EducationRepository;
import com.portfolio.api.service.interfaces.IEducationService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class EducationService implements IEducationService {

  @Autowired
  EducationRepository educationRepository;

  @Override
  public void createEducation(Education education) {
    educationRepository.save(education);
  }

  @Override
  public void editEducation(Education education) {
    educationRepository.save(education);
  }

  @Override
  public ArrayList<Education> listEducations() {
    return (ArrayList<Education>) educationRepository.findAll();
  }

  @Override
  public Optional<Education> findEducation(Long Id) {
    return educationRepository.findById(Id);
  }

  @Override
  public void deleteEducation(Long id) {
    educationRepository.deleteById(id);
  }

}
