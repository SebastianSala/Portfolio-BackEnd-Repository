package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Project;
import java.util.List;

public interface IProjectService {

  public void create(Project project);

  public void edit(Project project);

  public List<Project> listByPersonEmail(String personEmail);

  public List<Project> listByPersonId(Long personId);

  public Project findById(Long id);

  public Project findByPersonIdByProjectId(Long personId, Long projectId);

  public boolean existsByPersonIdByProjectId(Long personId, Long projectId);

  public boolean existsById(Long id);

  public boolean existsByPersonId(Long personId);

  public void delete(Long id);

  public void deleteByPersonId(Long personId);

}
