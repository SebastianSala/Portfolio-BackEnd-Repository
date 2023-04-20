package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Project;
import java.util.ArrayList;
import java.util.Optional;

public interface IProjectService {

  public void createProject(Project project);

  public void editProject(Project project);

  public ArrayList<Project> listProjects();

  public Optional<Project> findProject(Long Id);

  public void deleteProject(Long id);

}
