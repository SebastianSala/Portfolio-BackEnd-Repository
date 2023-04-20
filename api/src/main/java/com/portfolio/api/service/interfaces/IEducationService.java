package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Education;
import java.util.ArrayList;
import java.util.Optional;

public interface IEducationService {

  public void createEducation(Education education);

  public void editEducation(Education education);

  public ArrayList<Education> listEducations();

  public Optional<Education> findEducation(Long Id);

  public void deleteEducation(Long id);

}
