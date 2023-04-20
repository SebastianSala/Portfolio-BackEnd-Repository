package com.portfolio.api.service;

import com.portfolio.api.entity.Project;
import com.portfolio.api.repository.ProjectRepository;
import com.portfolio.api.service.interfaces.IProjectService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProjectService implements IProjectService {

  @Autowired
  ProjectRepository projectRepository;

  @Override
  public void createProject(Project project) {
    projectRepository.save(project);
  }

  @Override
  public void editProject(Project project) {
    projectRepository.save(project);
  }

  @Override
  public ArrayList<Project> listProjects() {
    return (ArrayList<Project>) projectRepository.findAll();
  }

  @Override
  public Optional<Project> findProject(Long Id) {
    return projectRepository.findById(Id);
  }

  @Override
  public void deleteProject(Long id) {
    projectRepository.deleteById(id);
  }

}
