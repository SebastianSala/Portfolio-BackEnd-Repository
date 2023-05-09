package com.portfolio.api.controller;

import com.portfolio.api.dto.Message;
import com.portfolio.api.entity.Person;
import com.portfolio.api.service.PersonService;
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
@RequestMapping("person")
@CrossOrigin("*")//(origins = {"https://sebastiansala-portfolio.web.app","http://localhost:4200"})
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping("/create")
  public ResponseEntity createPerson(@RequestBody Person person) {

    if (this.personService.existsByEmail(person.getEmail())) {
//      return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
      return new ResponseEntity<>(new Message("Ese email ya existe, ingrese uno distinto"), HttpStatus.BAD_REQUEST);
    }

    this.personService.createPerson(person);
//    return ResponseEntity.ok("Persona creada con exito = " + person.getId());
//    return ResponseEntity.ok(new Message("Persona creada con exito = " + person.getEmail()));
    return new ResponseEntity(new Message("Usuario " + person.getName() + " creado con exito:  " + person.getEmail()), HttpStatus.OK);
  }

//  @PutMapping("/edit")
//  public ResponseEntity editPerson(@RequestBody Person person) {
//    this.personService.editPerson(person);
//    return ResponseEntity.ok("edicion ok = " + person.getId());
//  }
  @PutMapping("/edit/{id}")
  public ResponseEntity<?> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {

//    Optional<Person> personData = this.personService.findPerson(id);
    if (person.getId() != id) {
      return new ResponseEntity("No match for Id of person (" + person.getId() + ") and Id of Request: " + id, HttpStatus.BAD_REQUEST);
    }

    if (personService.existsById(id)) {
//    
      if (this.personService.existsByEmailAndIdNot(person.getEmail(), id)) {
        return new ResponseEntity<>("El email ya existe", HttpStatus.NOT_ACCEPTABLE);
      }
      this.personService.editPerson(person);
      return new ResponseEntity("Person updated", HttpStatus.OK);

    } else {
      return new ResponseEntity<>("No person to edit of Id: " + id, HttpStatus.NOT_FOUND);
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
  public ResponseEntity<Person> findPerson(@PathVariable("id") Long id) {

    Optional<Person> personData = this.personService.findPerson(id);
    if (personData.isPresent()) {
      Person persons = personData.get();
      return new ResponseEntity<>(persons, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

//  @GetMapping("/list/person")
//  @ResponseBody
//  public ResponseEntity findPersonByIdByEmail(@RequestParam("id") Long id, @RequestParam("email") String email) {
//
//    Person personData = this.personService.finPersonByIdAndEmail(id, email);
//
//    return (personData != null
//        ? new ResponseEntity<>(personData, HttpStatus.OK)
//        : new ResponseEntity<>(HttpStatus.NOT_FOUND));
//
//  }
  @GetMapping("/list/person")
  @ResponseBody
  public ResponseEntity findPersonByEmail(@RequestParam("email") String email) {

    Person personData = this.personService.finPersonByEmail(email);
    //deleting the password for safety
    personData.setPassword("");

    return (personData != null
        ? new ResponseEntity<>(personData, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND));

  }

  @DeleteMapping("/delete")
  public ResponseEntity deletePersonById(@RequestParam("id") Long id) {

    if (personService.existsById(id)) {
      this.personService.deletePerson(id);
      String theId = String.valueOf(id);
//      return new ResponseEntity(Map.of("persona borrada = " : "id"), HttpStatus.OK);
      return new ResponseEntity(Map.of("deleted", theId), HttpStatus.OK);
    }
    return new ResponseEntity("No existe la persona de id: " + id, HttpStatus.NOT_FOUND);
//    return ResponseEntity.ok("persona borrada = " + id);
  }

//  @PostMapping("/login")
//  public ResponseEntity<String> logInObject(@RequestBody Person person) {
//
//    Person thePerson = this.personService.logInObject(person.getEmail(), person.getPassword());
//
//    if (thePerson != null) {
//      return new ResponseEntity<>("OK", HttpStatus.OK);
//    } else {
//      return new ResponseEntity<>("ERROR", HttpStatus.UNAUTHORIZED);
//    }
//  }
//  @PostMapping("/login")
//  public ResponseEntity<String> logIn(@RequestBody Person person) {
//
//    String loginString = this.personService.logInString(person.getEmail(), person.getPassword());
//
//    if ("OK".equals(loginString)) {
//      return new ResponseEntity<>(loginString, HttpStatus.OK);
//    } else if ("ERROR".equals(loginString)) {
//      return new ResponseEntity<>(loginString, HttpStatus.UNAUTHORIZED);
//    }
//
//    return null;
//  }
//
//}
  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity login(@RequestBody Person person) {

    Person loginPerson = this.personService.loginPerson(person.getEmail(), person.getPassword());

    return (loginPerson != null
        //        ? new ResponseEntity<>(loginPerson, HttpStatus.OK)
        ? new ResponseEntity<>(loginPerson, HttpStatus.OK)
        : new ResponseEntity<>(new Message("Usuario o password incorrectos"), HttpStatus.UNAUTHORIZED));

  }

}
