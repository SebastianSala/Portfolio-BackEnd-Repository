package com.portfolio.api.controller;

import com.portfolio.api.entity.Person;
import com.portfolio.api.entity.Project;
import com.portfolio.api.repository.PersonRepository;
import com.portfolio.api.repository.ProjectRepository;
import com.portfolio.api.service.PersonService;
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

  @Autowired
  PersonService personService;

  @PostMapping("/create")
  public ResponseEntity createProject(@RequestBody Project project) {

    Person thePerson = project.getPerson();
    if (thePerson instanceof Person) {
      this.projectService.createProject(project);
//      return ResponseEntity("Se creo el proyecto correctamente", HttpStatus.OK);
//      return ResponseEntity.ok(HttpStatus.OK);
      return new ResponseEntity("found!", HttpStatus.OK);
    } else {
//      return ResponseEntity<>("Se creo el proyecto correctamente", HttpStatus.NOT_FOUND);
//      return ResponseEntity.ok(HttpStatus.NOT_FOUND);
      return new ResponseEntity("No Persons with projects", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/person/{personId}/project")
//  public ResponseEntity<Project> createProject2(@PathVariable("personId") Long personId,
  public ResponseEntity createProjectByPersonId(@PathVariable("personId") Long personId,
      @RequestBody Project projectRequest) {

    Optional<Person> thePerson = personService.findPerson(personId);
    if (!thePerson.isPresent()) {
      return new ResponseEntity<>("No Person Found", HttpStatus.NOT_FOUND);
    }

    projectRequest.setPerson(thePerson.get());
    projectService.createProject(projectRequest);

//    return new ResponseEntity<>(projectRequest.getPerson(), HttpStatus.CREATED);
    return new ResponseEntity<>(projectRequest, HttpStatus.CREATED);

  }

//  @PutMapping("/edit")
//  public ResponseEntity editProject(@RequestBody Project project) {
//    this.projectService.editProject(project);
//    return ResponseEntity.ok("Proyecto actualizado" + project.getId());
//  }
  @PutMapping("/person/{personId}/project/{projectId}")
//  public ResponseEntity<Project> createProject2(@PathVariable("personId") Long personId,
  public ResponseEntity updateProject(
      @PathVariable("personId") Long personId,
      @RequestBody Project projectRequest,
      @PathVariable("projectId") Long projectId
  ) {

    Optional<Person> thePerson = personService.findPerson(personId);
    if (!thePerson.isPresent()) {
      return new ResponseEntity<>("No Person Found", HttpStatus.NOT_FOUND);
    }

    Optional<Project> theProject = projectService.findProject(projectId);
    if (!theProject.isPresent()) {
      return new ResponseEntity<>("No Project Found on any person", HttpStatus.NOT_FOUND);
    }

//    if (projectRequest.getId() != projectId) {
//      return new ResponseEntity<>("Atempet to edit incorrect. Person " + personId + " project: " + projectId + " vs: " + "Person: " + theProject.get().getPerson().getId() + " project: " + theProject.get().getId(), HttpStatus.NOT_FOUND);
//    }
//theProject.setPerson(thePerson.get());
//    if (theProject.get().getPerson().getId() == personId) {      
    if ((theProject.get().getPerson().getId() != personId)) {
//      projectRequest.setPerson(thePerson.get());
//      projectService.editProject(projectRequest);
      return new ResponseEntity<>("Project exists, but not on the person " + personId, HttpStatus.NOT_FOUND);
//      return new ResponseEntity<>(projectRequest, HttpStatus.CREATED);
    } else if ((projectRequest.getId() != projectId)) {
      return new ResponseEntity<>("Incorrect edit atempt", HttpStatus.NOT_FOUND);
    } else {
      projectRequest.setPerson(thePerson.get());
//      theProject.get().setPerson(thePerson.get());
//      theProject = projectRequest;
      projectService.editProject(projectRequest);
      return new ResponseEntity<>(projectRequest, HttpStatus.CREATED);
//      return new ResponseEntity<>("Project exists, but not on the person " + personId, HttpStatus.NOT_FOUND);
    }

//    projectRequest.setPerson(thePerson.get());
//    projectService.createProject(projectRequest);
//    return new ResponseEntity<>(projectRequest.getPerson(), HttpStatus.CREATED);
//    return new ResponseEntity<>(projectRequest, HttpStatus.CREATED);
  }

//  @GetMapping("/list")
//  @ResponseBody
//  public List<Project> listProjects() {
//    return this.projectService.listProjects();
//  }
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
  public ResponseEntity listProjectsByPersonId(@PathVariable("personId") Long personId) {

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
  public ResponseEntity findProjectByPersonIdById(
      @PathVariable("personId") Long personId,
      @PathVariable("projectId") Long projectId) {

    Project theProject = projectService.findByPersonIdByProjectId(personId, projectId);

    if (theProject instanceof Project) {
      return new ResponseEntity(theProject, HttpStatus.OK);
    } else {
      return new ResponseEntity("No project found", HttpStatus.NOT_FOUND);
    }

  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteProjectById(@RequestParam("id") Long id) {
    this.projectService.deleteProject(id);
    return ResponseEntity.ok("Proyecto " + id + " eliminado");
  }

  //@DeleteMapping("/")


}
