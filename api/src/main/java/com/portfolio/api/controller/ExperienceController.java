package com.portfolio.api.controller;

import com.portfolio.api.dto.response.MessageResponse;
import com.portfolio.api.entity.Experience;
import com.portfolio.api.entity.Person;
import com.portfolio.api.service.ExperienceService;
import com.portfolio.api.service.PersonService;
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
@RequestMapping("experience")
public class ExperienceController {

  @Autowired
  ExperienceService experienceService;

  @Autowired
  PersonService personService;

  @PostMapping("/person/{personId}/experience")
  public ResponseEntity<?> createExperienceByPersonId(@PathVariable("personId") Long personId,
      @RequestBody Experience experienceRequest) {

    Person thePerson = personService.findPerson(personId);

    if (thePerson == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    } else {

      experienceRequest.setPerson(thePerson);
      experienceService.create(experienceRequest);

      return new ResponseEntity<>(new MessageResponse("Ok. Experiencia laboral creada: " + experienceRequest.getPosition()), HttpStatus.CREATED);

    }

  }

  @PutMapping("/person/{personId}/experience/{experienceId}")
  public ResponseEntity<?> updateExperienceByPersonIdByExperienceId(
      @PathVariable("personId") Long personId,
      @PathVariable("experienceId") Long experienceId,
      @RequestBody Experience experienceRequest
  ) {

    Person thePerson = personService.findPerson(personId);
    if (thePerson == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    Experience theExperience = experienceService.findById(experienceId);
    if (theExperience == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No se encuentra la experiencia laboral de Id: " + experienceId), HttpStatus.NOT_FOUND);
    }

    // Check to see if the url and the id of the experience and person matches
    if ((theExperience.getPerson().getId() != personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. La experiencia laboral existe, pero no pertenece a este usuario." + personId), HttpStatus.BAD_REQUEST);
    } else if ((experienceRequest.getId() != experienceId)) {
      return new ResponseEntity<>(new MessageResponse("Error. Intento de edición incorrecto, revisar parámetros."), HttpStatus.BAD_REQUEST);

      // if everything is ok, procede
    } else {
      experienceRequest.setPerson(thePerson);
      experienceService.edit(experienceRequest);
      return new ResponseEntity<>(new MessageResponse("Ok. Experiencia laboral actualizada!"), HttpStatus.CREATED);
    }

  }

  @GetMapping("person/{personId}/list")
  @ResponseBody
  public ResponseEntity<?> getExperiencesByPersonId(@PathVariable("personId") Long personId) {

    if (!personService.existsById(personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    List<Experience> theExperiences = experienceService.listByPersonId(personId);

    if (theExperiences.isEmpty()) {
      return new ResponseEntity<>(new MessageResponse("Error. El usuario de Id: " + personId + " , no tiene experiencias laborales que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    theExperiences.forEach(experience -> experience.getPerson().clearPassword());
    return new ResponseEntity<>(theExperiences, HttpStatus.OK);

  }

  @GetMapping("person/{personEmail}")
  @ResponseBody
  public ResponseEntity<?> getExperiencesByPersonEmail(@PathVariable("personEmail") String personEmail) {

    if (!personService.existsByEmail(personEmail)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Email: " + personEmail), HttpStatus.NOT_FOUND);
    }

    List<Experience> theExperiences = experienceService.listByPersonEmail(personEmail);

    if (theExperiences.isEmpty()) {
      return new ResponseEntity<>(new MessageResponse("Error. El usuario de Email: " + personEmail + " , no tiene experiencias laborales que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    for (Experience experience : theExperiences) {
      Person person = experience.getPerson();
      person.clearPassword();
    }

    return new ResponseEntity<>(theExperiences, HttpStatus.OK);

  }

  @GetMapping("/person/{personId}/experience/{experienceId}")
  @ResponseBody
  public ResponseEntity<?> getExperienceByPersonIdByExperienceId(
      @PathVariable("personId") Long personId,
      @PathVariable("experienceId") Long experienceId) {

    Experience theExperience = this.experienceService.findByPersonIdByExperienceId(personId, experienceId);

    if (theExperience == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No se encuentra la experiencia laboral de Id: " + experienceId), HttpStatus.NOT_FOUND);
    } else {
      theExperience.getPerson().clearPassword();
      return new ResponseEntity<>(theExperience, HttpStatus.OK);
    }

  }

  //http://localhost:8080/experience/person/delete?personId=1&experienceId=1
  @DeleteMapping("/person/delete")
  public ResponseEntity<?> deleteExperienceByPersonIdByExperienceId(
      @RequestParam("personId") Long personId,
      @RequestParam("experienceId") Long experienceId
  ) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.experienceService.existsById(experienceId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe la experiencia laboral de Id: " + experienceId), HttpStatus.NOT_FOUND);
    }

    if (!this.experienceService.existsByPersonIdByExperienceId(personId, experienceId)) {
      // if the project does not exist on the person, return not found
      return new ResponseEntity<>(new MessageResponse("Error. No existe la experiencia laboral de Id: " + experienceId + " del usuario: " + personId), HttpStatus.NOT_FOUND);

    } else {
      // Everything ok, then return ok
      this.experienceService.delete(experienceId);

      return new ResponseEntity<>(new MessageResponse("Ok. Experiencia laboral borrada: " + experienceId), HttpStatus.OK);
    }

  }

  //http://localhost:8080/experience/person/1/delete
  @DeleteMapping("/person/{personId}/delete")
  public ResponseEntity<?> deleteExperiencesByPersonId(@PathVariable("personId") Long personId) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe la persona de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.experienceService.existsByPersonId(personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe la experiencia laboral del usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    this.experienceService.deleteByPersonId(personId);
    return new ResponseEntity<>(new MessageResponse("Ok. Eliminadas todas las experiencias laborales del usuario: " + personId), HttpStatus.OK);

  }

}
