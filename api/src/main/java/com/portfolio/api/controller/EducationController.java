package com.portfolio.api.controller;

import com.portfolio.api.dto.Message;
import com.portfolio.api.entity.Education;
import com.portfolio.api.entity.Person;
import com.portfolio.api.service.EducationService;
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
@RequestMapping("education")
public class EducationController {

  @Autowired
  EducationService educationService;

  @Autowired
  PersonService personService;

  @PostMapping("/person/{personId}/education")
  public ResponseEntity<?> createEducationByPersonId(@PathVariable("personId") Long personId,
      @RequestBody Education educationRequest) {

    Person thePerson = personService.findPerson(personId);

    if (thePerson == null) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    } else {

      educationRequest.setPerson(thePerson);
      educationService.create(educationRequest);

      return new ResponseEntity<>(new Message("Ok. Estudio agregado: " + educationRequest.getTitle()), HttpStatus.CREATED);

    }

  }

  @PutMapping("/person/{personId}/education/{educationId}")
  public ResponseEntity<?> updateEducationByPersonIdByEducationId(
      @PathVariable("personId") Long personId,
      @PathVariable("educationId") Long educationId,
      @RequestBody Education educationRequest
  ) {

    Person thePerson = personService.findPerson(personId);
    if (thePerson == null) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    Education theEducation = educationService.findById(educationId);
    if (theEducation == null) {
      return new ResponseEntity<>(new Message("Error. No se encuentra el estudio de Id: " + educationId), HttpStatus.NOT_FOUND);
    }

    // Check to see if the url and the id of the education and person matches
    if ((theEducation.getPerson().getId() != personId)) {
      return new ResponseEntity<>(new Message("Error. El estudio existe, pero no pertenece a este usuario." + personId), HttpStatus.BAD_REQUEST);
    } else if ((educationRequest.getId() != educationId)) {
      return new ResponseEntity<>(new Message("Error. Intento de edición incorrecto, revisar parámetros."), HttpStatus.BAD_REQUEST);

      // if everything is ok, procede
    } else {
      educationRequest.setPerson(thePerson);
      educationService.edit(educationRequest);
      return new ResponseEntity<>(new Message("Ok. Estudio actualizado!"), HttpStatus.CREATED);
    }

  }

  @GetMapping("person/{personId}/list")
  @ResponseBody
  public ResponseEntity<?> getEducationsByPersonId(@PathVariable("personId") Long personId) {

    if (!personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    List<Education> theEducations = educationService.listByPersonId(personId);

    if (theEducations.isEmpty()) {
      return new ResponseEntity<>(new Message("Error. El usuario de Id: " + personId + " , no tiene estudios que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    theEducations.forEach(education -> education.getPerson().clearPassword());
    return new ResponseEntity<>(theEducations, HttpStatus.OK);

  }

  @GetMapping("person/{personEmail}")
  @ResponseBody
  public ResponseEntity<?> getEducationsByPersonEmail(@PathVariable("personEmail") String personEmail) {

    if (!personService.existsByEmail(personEmail)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Email: " + personEmail), HttpStatus.NOT_FOUND);
    }

    List<Education> theEducations = educationService.listByPersonEmail(personEmail);

    if (theEducations.isEmpty()) {
      return new ResponseEntity<>(new Message("Error. El usuario de Email: " + personEmail + " , no tiene estudios que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    for (Education education : theEducations) {
      Person person = education.getPerson();
      person.clearPassword();
    }

    return new ResponseEntity<>(theEducations, HttpStatus.OK);

  }

  @GetMapping("/person/{personId}/education/{educationId}")
  @ResponseBody
  public ResponseEntity<?> getEducationByPersonIdByEducationId(
      @PathVariable("personId") Long personId,
      @PathVariable("educationId") Long educationId) {

    Education theEducation = this.educationService.findByPersonIdByEducationId(personId, educationId);

    if (theEducation == null) {
      return new ResponseEntity<>(new Message("Error. No se encuentra el estudio de Id: " + educationId), HttpStatus.NOT_FOUND);
    } else {
      theEducation.getPerson().clearPassword();
      return new ResponseEntity<>(theEducation, HttpStatus.OK);
    }

  }

  //http://localhost:8080/education/person/delete?personId=1&educationId=1
  @DeleteMapping("/person/delete")
  public ResponseEntity<?> deleteEducationByPersonIdByEducationId(
      @RequestParam("personId") Long personId,
      @RequestParam("educationId") Long educationId
  ) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.educationService.existsById(educationId)) {
      return new ResponseEntity<>(new Message("Error. No existe el estudio de Id: " + educationId), HttpStatus.NOT_FOUND);
    }

    if (!this.educationService.existsByPersonIdByEducationId(personId, educationId)) {
      // if the project does not exist on the person, return not found
      return new ResponseEntity<>(new Message("Error. No existe el estudio de Id: " + educationId + " del usuario: " + personId), HttpStatus.NOT_FOUND);

    } else {
      // Everything ok, then return ok
      this.educationService.delete(educationId);

      return new ResponseEntity<>(new Message("Ok. Estudio borrado: " + educationId), HttpStatus.OK);
    }

  }

  //http://localhost:8080/education/person/1/delete
  @DeleteMapping("/person/{personId}/delete")
  public ResponseEntity<?> deleteEducationsByPersonId(@PathVariable("personId") Long personId) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe la persona de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.educationService.existsByPersonId(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe el estudio del usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    this.educationService.deleteByPersonId(personId);
    return new ResponseEntity<>(new Message("Ok. Eliminados todos los estudios del usuario: " + personId), HttpStatus.OK);

  }

}
