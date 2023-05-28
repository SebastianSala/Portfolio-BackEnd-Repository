package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Education;
import java.util.List;

public interface IEducationService {

  public void create(Education education);

  public void edit(Education education);

  public List<Education> listByPersonEmail(String personEmail);

  public List<Education> listByPersonId(Long personId);

  public Education findById(Long id);

  public Education findByPersonIdByEducationId(Long personId, Long educationId);

  public boolean existsByPersonIdByEducationId(Long personId, Long educationId);

  public boolean existsById(Long id);

  public boolean existsByPersonId(Long personId);

  public void delete(Long id);

  public void deleteByPersonId(Long personId);

}
