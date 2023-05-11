package com.portfolio.api.controller;

import com.portfolio.api.dto.Message;
import com.portfolio.api.entity.Person;
import com.portfolio.api.service.PersonService;
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
@RequestMapping("person")
@CrossOrigin("*")//(origins = {"https://sebastiansala-portfolio.web.app","http://localhost:4200"})
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping("/create")
  public ResponseEntity createPerson(@RequestBody Person person) {

    if (this.personService.existsByEmail(person.getEmail())) {
      return new ResponseEntity<>(new Message("Ese email ya existe, ingrese uno distinto"), HttpStatus.BAD_REQUEST);
    }

    this.personService.createPerson(person);
    return new ResponseEntity(new Message("Usuario " + person.getName() + " creado con exito:  " + person.getEmail()), HttpStatus.OK);
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<?> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {

    if (person.getId() != id) {
      return new ResponseEntity(new Message("No hay coincidencia entre usuario de Id " + person.getId() + " y Id de Request: " + id), HttpStatus.BAD_REQUEST);
    }

    if (personService.existsById(id)) {

      //Checks if a person with the same mail already exist, excluding the current one by its "id" that will have the same email of course
      if (this.personService.existsByEmailAndIdNot(person.getEmail(), id)) {
        return new ResponseEntity<>(new Message("Error, el email ya existe, ingrese uno distinto"), HttpStatus.BAD_REQUEST);
      }
      this.personService.editPerson(person);
      return new ResponseEntity(new Message("Usuario actualizado: " + person.getEmail()), HttpStatus.OK);

    } else {
      return new ResponseEntity<>(new Message("No existe usuario para editar de Id: " + id), HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/list")
  @ResponseBody
  public ResponseEntity<List<Person>> listPersons() {
    List<Person> persons = this.personService.listPersons();

    if (persons.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    persons.forEach(Person::clearPassword);

    return new ResponseEntity<>(persons, HttpStatus.OK);
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public ResponseEntity<?> findPerson(@PathVariable("id") Long id) {

    Optional<Person> personData = this.personService.findPerson(id);
    if (personData.isPresent()) {
      Person person = personData.get();

      //deleting the password for safety
      person.clearPassword();

      return new ResponseEntity<>(person, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new Message("No existe usuario con id: " + id), HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/list/person")
  @ResponseBody
  public ResponseEntity findPersonByEmail(@RequestParam("email") String email) {

    Person personData = this.personService.finPersonByEmail(email);

    //deleting the password for safety
    if (personData != null) {
      personData.clearPassword();
    }

    return (personData != null
        ? new ResponseEntity<>(personData, HttpStatus.OK)
        : new ResponseEntity<>(new Message("No existe usuario con el email: " + email), HttpStatus.NOT_FOUND));

  }

  @DeleteMapping("/delete")
  public ResponseEntity deletePersonById(@RequestParam("id") Long id) {

    if (personService.existsById(id)) {
      this.personService.deletePerson(id);
      String theId = String.valueOf(id);
      return new ResponseEntity(new Message("Usuario borrado: " + theId), HttpStatus.OK);
    }
    return new ResponseEntity(new Message("No existe el usuario de Id: " + id), HttpStatus.NOT_FOUND);
  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity login(@RequestBody Person person) {

    Person loginPerson = this.personService.loginPerson(person.getEmail(), person.getPassword());

    //deleting the password for safety
    if (loginPerson != null) {
      loginPerson.clearPassword();
    }

    return (loginPerson != null
        ? new ResponseEntity<>(loginPerson, HttpStatus.OK)
        : new ResponseEntity<>(new Message("Email o contraseña incorrectos"), HttpStatus.UNAUTHORIZED));

  }

}
