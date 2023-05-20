package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Experience;
import java.util.List;

public interface IExperienceService {

  public void create(Experience experience);

  public void edit(Experience experience);

  public List<Experience> listByPersonEmail(String personEmail);

  public List<Experience> listByPersonId(Long personId);

  public Experience findById(Long id);

  public Experience findByPersonIdByExperienceId(Long personId, Long experienceId);

  public boolean existsByPersonIdByExperienceId(Long personId, Long experienceId);

  public boolean existsById(Long id);

  public boolean existsByPersonId(Long personId);

  public void delete(Long id);

  public void deleteByPersonId(Long personId);
}
