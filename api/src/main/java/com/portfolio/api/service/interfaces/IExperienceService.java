package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Experience;
import java.util.ArrayList;
import java.util.Optional;

public interface IExperienceService {

  public void createExperience(Experience experience);
  
  public void editExperience(Experience experience);
  
  public ArrayList<Experience> listExperiences();
  
  public Optional<Experience> findExperience(Long id);
  
  public void deleteExperience(Long id);

}
