package com.portfolio.api.service;

import com.portfolio.api.entity.Project;
import com.portfolio.api.repository.ProjectRepository;
import com.portfolio.api.service.interfaces.IProjectService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class ProjectService implements IProjectService {

  @Autowired
  ProjectRepository projectRepository;

  @Override
  public void create(Project project) {
    projectRepository.save(project);
  }

  @Override
  public void edit(Project project) {
    projectRepository.save(project);
  }

  @Override
  public Project findById(Long id) {
    return projectRepository.findById(id).orElse(null);
  }

  @Override
  public List<Project> listByPersonEmail(String personEmail) {
    return this.projectRepository.findByPersonEmail(personEmail);
  }

  @Override
  public List<Project> listByPersonId(Long personId) {
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

  @Override
  public boolean existsByPersonId(Long personId) {
    return this.projectRepository.existsByPersonId(personId);
  }

  @Override
  public void delete(Long id) {
    projectRepository.deleteById(id);
  }

  @Override
  public void deleteByPersonId(Long personId) {
    this.projectRepository.deleteByPersonId(personId);
  }

}
