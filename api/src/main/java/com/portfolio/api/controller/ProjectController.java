package com.portfolio.api.controller;

import com.portfolio.api.entity.Person;
import com.portfolio.api.entity.Project;
import com.portfolio.api.repository.ProjectRepository;
import com.portfolio.api.service.PersonService;
import com.portfolio.api.service.ProjectService;
import java.util.List;
import java.util.Map;
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
@CrossOrigin("*")//(origins = {"https://sebastiansala-portfolio.web.app","http://localhost:4200"})
public class ProjectController {

  @Autowired
  ProjectService projectService;

  @Autowired
  PersonService personService;

  @PostMapping("/create")
  public ResponseEntity createProject(@RequestBody Project project) {

    Person thePerson = null;
    thePerson = project.getPerson();
    if (thePerson != null) {
      this.projectService.createProject(project);
      return new ResponseEntity("found!", HttpStatus.OK);
    } else {

      return new ResponseEntity("No Persons with projects", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/person/{personId}/project")
  public ResponseEntity createProjectByPersonId(@PathVariable("personId") Long personId,
      @RequestBody Project projectRequest) {

    Optional<Person> thePerson = personService.findPerson(personId);
    if (!thePerson.isPresent()) {
      return new ResponseEntity<>("No Person Found", HttpStatus.NOT_FOUND);
    }

    projectRequest.setPerson(thePerson.get());
    projectService.createProject(projectRequest);

//    return new ResponseEntity<>(projectRequest, HttpStatus.CREATED);
    return new ResponseEntity<>(projectRequest, HttpStatus.CREATED);

  }

  @PutMapping("/person/{personId}/project/{projectId}")
  public ResponseEntity updateProjectByPersonIdByProjectId(
      @PathVariable("personId") Long personId,
      @PathVariable("projectId") Long projectId,
      @RequestBody Project projectRequest
  ) {

    Optional<Person> thePerson = personService.findPerson(personId);
    if (!thePerson.isPresent()) {
      return new ResponseEntity<>("No Person Found", HttpStatus.NOT_FOUND);
    }

    Optional<Project> theProject = projectService.findProject(projectId);
    if (!theProject.isPresent()) {
      return new ResponseEntity<>("No Project Found on any person", HttpStatus.NOT_FOUND);
    }

    if ((theProject.get().getPerson().getId() != personId)) {
      return new ResponseEntity<>("Project exists, but not on the person " + personId, HttpStatus.NOT_FOUND);
    } else if ((projectRequest.getId() != projectId)) {
      return new ResponseEntity<>("Incorrect edit atempt", HttpStatus.NOT_FOUND);
    } else {
      projectRequest.setPerson(thePerson.get());
      projectService.editProject(projectRequest);
      return new ResponseEntity<>(projectRequest, HttpStatus.CREATED);
    }

  }

  @GetMapping("/list")
  @ResponseBody
  public ResponseEntity<List<Project>> listProjects() {
    List<Project> projects = this.projectService.listProjects();

    if (projects.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(projects, HttpStatus.OK);
  }

  @GetMapping("person/{personId}/list")
  @ResponseBody
  public ResponseEntity getProjectsByPersonId(@PathVariable("personId") Long personId) {

    if (!personService.existsById(personId)) {
      return new ResponseEntity("no person with id: " + personId, HttpStatus.NOT_FOUND);
    }

    List<Project> theProjects = projectService.findByPersonId(personId);

    if (theProjects.isEmpty()) {
      return new ResponseEntity("The person with id: " + personId + " , has no projects to show", HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity(theProjects, HttpStatus.OK);
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public Optional<Project> findProject(@PathVariable Long id) {
    return this.projectService.findProject(id);
  }

  @GetMapping("/person/{personId}/project/{projectId}")
  @ResponseBody
  public ResponseEntity getProjectByPersonIdByProjectId(
      @PathVariable("personId") Long personId,
      @PathVariable("projectId") Long projectId) {

    Project theProject = null;
    theProject = this.projectService.findByPersonIdByProjectId(personId, projectId);

    if (theProject != null) {
      return new ResponseEntity(theProject, HttpStatus.OK);
    } else {
      return new ResponseEntity("No project found", HttpStatus.NOT_FOUND);
    }

  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteProjectById(@RequestParam("id") Long id) {
    if (this.projectService.existsById(id)) {
      this.projectService.deleteProject(id);
      return new ResponseEntity("Proyecto " + id + " eliminado", HttpStatus.OK);
    } else {
      return new ResponseEntity("No existe el proyecto: " + id, HttpStatus.NOT_FOUND);
    }

  }

  @DeleteMapping("/person/delete")
  //http://localhost:8080/project/person/delete?personId=1&projectId=1
  public ResponseEntity deleteProjectByPersonIdByProjectId(
      @RequestParam("personId") Long personId,
      @RequestParam("projectId") Long projectId
  ) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>("No existe la persona de Id: " + personId, HttpStatus.NOT_FOUND);
    }

    if (!this.projectService.existsById(projectId)) {
      return new ResponseEntity("No existe el projecto de Id: " + projectId, HttpStatus.NOT_FOUND);
    }

    if (this.projectService.existsByPersonIdByProjectId(personId, projectId)) {
      this.projectService.deleteProject(projectId);
    } else {
      return new ResponseEntity("No existe el projecto de Id: " + projectId + " de la persona: " + personId, HttpStatus.NOT_FOUND);
    }

//    return new ResponseEntity("Deleted Project: " + projectId + " of Person: " + personId, HttpStatus.OK);
    return new ResponseEntity<>(Map.of("value", "SOME TEXT"), HttpStatus.OK);
  }

  @Autowired
  ProjectRepository projectRepository;

  @DeleteMapping("/person/{personId}/delete")
  //http://localhost:8080/project/person/delete?personId=1&projectId=1
  public ResponseEntity deleteProjectsByPersonId(@PathVariable("personId") Long personId) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>("No existe la persona de Id: " + personId, HttpStatus.NOT_FOUND);
    }

    if (!this.projectRepository.existsByPersonId(personId)) {
      return new ResponseEntity("No existe el projectos de la persona de Id: " + personId, HttpStatus.NOT_FOUND);
    }

//    this.projectService.deleteProject(personId);
      this.projectRepository.deleteByPersonId(personId);

    return new ResponseEntity("Deleted all project: of Person: " + personId, HttpStatus.OK);
  }

}
