package com.portfolio.api.service;

import com.portfolio.api.entity.Project;
import com.portfolio.api.repository.PersonRepository;
import com.portfolio.api.repository.ProjectRepository;
import com.portfolio.api.service.interfaces.IProjectService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class ProjectService implements IProjectService {

  @Autowired
  ProjectRepository projectRepository;

  @Autowired
  PersonRepository personRepository;

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

  @Override
  public List<Project> findByPersonId(Long personId) {
    return projectRepository.findByPersonId(personId);
  }

  @Override
  public Project findByPersonIdByProjectId(Long personId, Long projectId) {
    return projectRepository.findByPersonIdAndId(personId, projectId);
  }

  @Override
  public boolean existsById(Long id) {
    return this.projectRepository.existsById(id);
  }
  
  @Override
  public boolean existsByPersonIdByProjectId(Long personId, Long projectId) {
    return this.projectRepository.existsByPersonIdAndId(personId, projectId);
  }

}
