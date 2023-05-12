package com.portfolio.api.controller;

import com.portfolio.api.dto.Message;
import com.portfolio.api.entity.Person;
import com.portfolio.api.entity.Project;
import com.portfolio.api.service.PersonService;
import com.portfolio.api.service.ProjectService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
// controlling cross origin from WebConfig class
//@CrossOrigin("*")//(origins = {"https://sebastiansala-portfolio.web.app","http://localhost:4200"})
public class ProjectController {

  @Autowired
  ProjectService projectService;

  @Autowired
  PersonService personService;

  @PostMapping("/person/{personId}/project")
  public ResponseEntity<?> createProjectByPersonId(@PathVariable("personId") Long personId,
      @RequestBody Project projectRequest) {

    Person thePerson = personService.findPerson(personId);

    if (thePerson == null) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    } else {

      projectRequest.setPerson(thePerson);
      projectService.create(projectRequest);

      return new ResponseEntity<>(new Message("Ok. Proyecto creado: " + projectRequest.getName()), HttpStatus.CREATED);

    }

  }

  @PutMapping("/person/{personId}/project/{projectId}")
  public ResponseEntity<?> updateProjectByPersonIdByProjectId(
      @PathVariable("personId") Long personId,
      @PathVariable("projectId") Long projectId,
      @RequestBody Project projectRequest
  ) {

    Person thePerson = personService.findPerson(personId);
    if (thePerson == null) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    Project theProject = projectService.findById(projectId);
    if (theProject == null) {
      return new ResponseEntity<>(new Message("Error. No se encuentra el proyecto de Id: " + projectId), HttpStatus.NOT_FOUND);
    }

    // Check to see if the url and the id of the project and person matches
    if ((theProject.getPerson().getId() != personId)) {
      return new ResponseEntity<>(new Message("Error. El proyecto existe, pero no pertenece a este usuario." + personId), HttpStatus.BAD_REQUEST);
    } else if ((projectRequest.getId() != projectId)) {
      return new ResponseEntity<>(new Message("Error. Intento de edición incorrecto, revisar parámetros."), HttpStatus.BAD_REQUEST);

      // if everything is ok, procede
    } else {
      projectRequest.setPerson(thePerson);
      projectService.edit(projectRequest);
      return new ResponseEntity<>(new Message("Ok. Proyecto actualizado!"), HttpStatus.CREATED);
    }

  }

  @GetMapping("person/{personId}/list")
  @ResponseBody
  public ResponseEntity<?> getProjectsByPersonId(@PathVariable("personId") Long personId) {

    if (!personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    List<Project> theProjects = projectService.listByPersonId(personId);

    if (theProjects.isEmpty()) {
      return new ResponseEntity<>(new Message("Error. El usuario de Id: " + personId + " , no tiene proyectos que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    theProjects.forEach(project -> project.getPerson().clearPassword());
    return new ResponseEntity<>(theProjects, HttpStatus.OK);

  }

  @GetMapping("person/{personEmail}")
  @ResponseBody
  public ResponseEntity<?> getProjectsByPersonEmail(@PathVariable("personEmail") String personEmail) {

    if (!personService.existsByEmail(personEmail)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Email: " + personEmail), HttpStatus.NOT_FOUND);
    }

    List<Project> theProjects = projectService.listByPersonEmail(personEmail);

    if (theProjects.isEmpty()) {
      return new ResponseEntity<>(new Message("Error. El usuario de Email: " + personEmail + " , no tiene proyectos que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    for (Project project : theProjects) {
      Person person = project.getPerson();
      person.clearPassword();
    }

    return new ResponseEntity<>(theProjects, HttpStatus.OK);

  }

  @GetMapping("/person/{personId}/project/{projectId}")
  @ResponseBody
  public ResponseEntity<?> getProjectByPersonIdByProjectId(
      @PathVariable("personId") Long personId,
      @PathVariable("projectId") Long projectId) {

    Project theProject = this.projectService.findByPersonIdByProjectId(personId, projectId);

    if (theProject == null) {
      return new ResponseEntity<>(new Message("Error. No se encuentra el proyecto de Id: " + projectId), HttpStatus.NOT_FOUND);
    } else {
      theProject.getPerson().clearPassword();
      return new ResponseEntity<>(theProject, HttpStatus.OK);
    }

  }

  //http://localhost:8080/project/person/delete?personId=1&projectId=1
  @DeleteMapping("/person/delete")
  public ResponseEntity<?> deleteProjectByPersonIdByProjectId(
      @RequestParam("personId") Long personId,
      @RequestParam("projectId") Long projectId
  ) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.projectService.existsById(projectId)) {
      return new ResponseEntity<>(new Message("Error. No existe el projecto de Id: " + projectId), HttpStatus.NOT_FOUND);
    }

    if (!this.projectService.existsByPersonIdByProjectId(personId, projectId)) {
      // if the proyect does not exist on the person, return not found
      return new ResponseEntity<>(new Message("Error. No existe el projecto de Id: " + projectId + " del usuario: " + personId), HttpStatus.NOT_FOUND);

    } else {
      // Everything ok, then return ok
      this.projectService.delete(projectId);

      return new ResponseEntity<>(new Message("Ok. Projecto borrado: " + projectId), HttpStatus.OK);
    }

  }

  //http://localhost:8080/project/person/1/delete
  @DeleteMapping("/person/{personId}/delete")
  public ResponseEntity<?> deleteProjectsByPersonId(@PathVariable("personId") Long personId) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe la persona de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.projectService.existsByPersonId(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe el projectos del usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    this.projectService.deleteByPersonId(personId);
    return new ResponseEntity<>(new Message("Ok. Eliminados todos los proyectos del usuario: " + personId), HttpStatus.OK);

  }

}
