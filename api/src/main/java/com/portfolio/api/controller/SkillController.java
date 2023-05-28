package com.portfolio.api.controller;

import com.portfolio.api.dto.Message;
import com.portfolio.api.entity.Skill;
import com.portfolio.api.entity.Person;
import com.portfolio.api.service.PersonService;
import com.portfolio.api.service.SkillService;
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
@RequestMapping("skill")
public class SkillController {

  @Autowired
  SkillService skillService;

  @Autowired
  PersonService personService;

  @PostMapping("/person/{personId}/skill")
  public ResponseEntity<?> createSkillByPersonId(@PathVariable("personId") Long personId,
      @RequestBody Skill skillRequest) {

    Person thePerson = personService.findPerson(personId);

    if (thePerson == null) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    } else {

      skillRequest.setPerson(thePerson);
      skillService.create(skillRequest);

      return new ResponseEntity<>(new Message("Ok. Habilidad creada: " + skillRequest.getName()), HttpStatus.CREATED);

    }

  }

  @PutMapping("/person/{personId}/skill/{skillId}")
  public ResponseEntity<?> updateSkillByPersonIdBySkillId(
      @PathVariable("personId") Long personId,
      @PathVariable("skillId") Long skillId,
      @RequestBody Skill skillRequest
  ) {

    Person thePerson = personService.findPerson(personId);
    if (thePerson == null) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    Skill theSkill = skillService.findById(skillId);
    if (theSkill == null) {
      return new ResponseEntity<>(new Message("Error. No se encuentra la habilidad de Id: " + skillId), HttpStatus.NOT_FOUND);
    }

    // Check to see if the url and the id of the skill and person matches
    if ((theSkill.getPerson().getId() != personId)) {
      return new ResponseEntity<>(new Message("Error. La habilidad existe, pero no pertenece a este usuario." + personId), HttpStatus.BAD_REQUEST);
    } else if ((skillRequest.getId() != skillId)) {
      return new ResponseEntity<>(new Message("Error. Intento de edición incorrecto, revisar parámetros."), HttpStatus.BAD_REQUEST);

      // if everything is ok, procede
    } else {
      skillRequest.setPerson(thePerson);
      skillService.edit(skillRequest);
      return new ResponseEntity<>(new Message("Ok. Habilidad actualizada!"), HttpStatus.CREATED);
    }

  }

  @GetMapping("person/{personId}/list")
  @ResponseBody
  public ResponseEntity<?> getSkillsByPersonId(@PathVariable("personId") Long personId) {

    if (!personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    List<Skill> theSkills = skillService.listByPersonId(personId);

    if (theSkills.isEmpty()) {
      return new ResponseEntity<>(new Message("Error. El usuario de Id: " + personId + " , no tiene habilidades que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    theSkills.forEach(skill -> skill.getPerson().clearPassword());
    return new ResponseEntity<>(theSkills, HttpStatus.OK);

  }

  @GetMapping("person/{personEmail}")
  @ResponseBody
  public ResponseEntity<?> getSkillsByPersonEmail(@PathVariable("personEmail") String personEmail) {

    if (!personService.existsByEmail(personEmail)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Email: " + personEmail), HttpStatus.NOT_FOUND);
    }

    List<Skill> theSkills = skillService.listByPersonEmail(personEmail);

    if (theSkills.isEmpty()) {
      return new ResponseEntity<>(new Message("Error. El usuario de Email: " + personEmail + " , no tiene habilidades que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    for (Skill skill : theSkills) {
      Person person = skill.getPerson();
      person.clearPassword();
    }

    return new ResponseEntity<>(theSkills, HttpStatus.OK);

  }

  @GetMapping("/person/{personId}/skill/{skillId}")
  @ResponseBody
  public ResponseEntity<?> getSkillByPersonIdBySkillId(
      @PathVariable("personId") Long personId,
      @PathVariable("skillId") Long skillId) {

    Skill theSkill = this.skillService.findByPersonIdBySkillId(personId, skillId);

    if (theSkill == null) {
      return new ResponseEntity<>(new Message("Error. No se encuentra la habilidad de Id: " + skillId), HttpStatus.NOT_FOUND);
    } else {
      theSkill.getPerson().clearPassword();
      return new ResponseEntity<>(theSkill, HttpStatus.OK);
    }

  }

  //http://localhost:8080/skill/person/delete?personId=1&skillId=1
  @DeleteMapping("/person/delete")
  public ResponseEntity<?> deleteSkillByPersonIdBySkillId(
      @RequestParam("personId") Long personId,
      @RequestParam("skillId") Long skillId
  ) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.skillService.existsById(skillId)) {
      return new ResponseEntity<>(new Message("Error. No existe la habilidad de Id: " + skillId), HttpStatus.NOT_FOUND);
    }

    if (!this.skillService.existsByPersonIdBySkillId(personId, skillId)) {
      // if the project does not exist on the person, return not found
      return new ResponseEntity<>(new Message("Error. No existe la habilidad de Id: " + skillId + " del usuario: " + personId), HttpStatus.NOT_FOUND);

    } else {
      // Everything ok, then return ok
      this.skillService.delete(skillId);

      return new ResponseEntity<>(new Message("Ok. Habilidades borrada: " + skillId), HttpStatus.OK);
    }

  }

  //http://localhost:8080/skill/person/1/delete
  @DeleteMapping("/person/{personId}/delete")
  public ResponseEntity<?> deleteSkillsByPersonId(@PathVariable("personId") Long personId) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe la persona de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.skillService.existsByPersonId(personId)) {
      return new ResponseEntity<>(new Message("Error. No existe la habilidad del usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    this.skillService.deleteByPersonId(personId);
    return new ResponseEntity<>(new Message("Ok. Eliminadas todas las habilidades del usuario: " + personId), HttpStatus.OK);

  }

}
