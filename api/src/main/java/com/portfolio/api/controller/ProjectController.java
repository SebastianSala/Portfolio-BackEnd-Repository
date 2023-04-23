package com.portfolio.api.controller;

import com.portfolio.api.entity.Project;
import com.portfolio.api.service.ProjectService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

  @Autowired
  ProjectService projectService;

  @PostMapping("/create")
  public ResponseEntity createProject(@RequestBody Project project) {
    this.projectService.createProject(project);
    return ResponseEntity.ok("Se creo el proyecto correctamente" + project.getId());
  }

  @PutMapping("/edit")
  public ResponseEntity editProject(@RequestBody Project project) {
    this.projectService.editProject(project);
    return ResponseEntity.ok("Proyecto actualizado" + project.getId());
  }

//  @GetMapping("/list")
//  @ResponseBody
//  public List<Project> listProjects() {
//    return this.projectService.listProjects();
//  }
  @GetMapping("/list")
  @ResponseBody
  public ResponseEntity<List<Project>> listProjects() {
    List<Project> projects =  this.projectService.listProjects();
    
    if (projects.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(projects, HttpStatus.OK);
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public Optional<Project> findProject(@PathVariable Long id) {
    return this.projectService.findProject(id);
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteProject(@RequestParam("id") Long id) {
    this.projectService.deleteProject(id);
    return ResponseEntity.ok("Proyecto " + id + " eliminado");
  }

}
