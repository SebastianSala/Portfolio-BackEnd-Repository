package com.portfolio.api.controller;

import com.portfolio.api.dto.Message;
import com.portfolio.api.entity.Person;
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
@RequestMapping("person")
// controlling cross origins from WebConfig class
//@CrossOrigin("*")//(origins = {"https://sebastiansala-portfolio.web.app","http://localhost:4200"})
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping("/create")
  public ResponseEntity<?> createPerson(@RequestBody Person person) {

    if (this.personService.existsByEmail(person.getEmail())) {
      return new ResponseEntity<>(new Message("Error. Ese email ya existe, ingrese uno distinto"), HttpStatus.BAD_REQUEST);
    }

    this.personService.createPerson(person);
    return new ResponseEntity<>(new Message("Ok. Usuario " + person.getName() + " creado con exito:  " + person.getEmail()), HttpStatus.OK);

  }

  //http://localhost:8080/person/edit/1
  @PutMapping("/edit/{id}")
  public ResponseEntity<?> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {

    if (person.getId() != id) {
      return new ResponseEntity<>(new Message("Error. No hay coincidencia entre usuario de Id " + person.getId() + " y Id de Request: " + id), HttpStatus.BAD_REQUEST);
    }

    if (this.personService.existsById(id)) {

      //Checks if a person with the same mail already exist, excluding the current one by its "id" that will have the same email of course
      if (this.personService.existsByEmailAndIdNot(person.getEmail(), id)) {
        return new ResponseEntity<>(new Message("Error. El email ya existe, ingrese uno distinto."), HttpStatus.BAD_REQUEST);
      }
      //Set the curent password to not overwrite it
      Person tempPerson = this.personService.findPerson(id);
      person.setPassword(tempPerson.getPassword());
      
      System.out.println(tempPerson.getPassword() + tempPerson.getEmail());
      System.out.println(person.getPassword() + person.getEmail());
      
      this.personService.editPerson(person);
      return new ResponseEntity<>(new Message("Ok. Usuario actualizado: " + person.getEmail()), HttpStatus.OK);

    } else {
      return new ResponseEntity<>(new Message("Error. No existe usuario para editar de Id: " + id), HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/list")
  @ResponseBody
  public ResponseEntity<?> listPersons() {
    List<Person> persons = this.personService.listPersons();

    if (persons.isEmpty()) {
      return new ResponseEntity<>(new Message("Error. No existen Usuarios. Cree una cuenta nueva"), HttpStatus.NOT_FOUND);
    }

    persons.forEach(Person::clearPassword);
    return new ResponseEntity<>(persons, HttpStatus.OK);

  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public ResponseEntity<?> findPerson(@PathVariable("id") Long id) {

    Person thePerson = this.personService.findPerson(id);

    if (thePerson == null) {
      return new ResponseEntity<>(new Message("Error. No existe usuario con id: " + id), HttpStatus.NOT_FOUND);

    } else {
      //deleting the password for safety
      thePerson.clearPassword();
      return new ResponseEntity<>(thePerson, HttpStatus.OK);
    }

  }

  @GetMapping("/list/person")
  @ResponseBody
  public ResponseEntity<?> findPersonByEmail(@RequestParam("email") String email) {

    Person thePerson = this.personService.finPersonByEmail(email);

    //deleting the password for safety
    if (thePerson != null) {
      thePerson.clearPassword();
    }

    return (thePerson != null
        ? new ResponseEntity<>(thePerson, HttpStatus.OK)
        : new ResponseEntity<>(new Message("Error. No existe usuario con el email: " + email), HttpStatus.NOT_FOUND));

  }

  @DeleteMapping("/delete")
  public ResponseEntity<?> deletePersonById(@RequestParam("id") Long id) {

    if (!personService.existsById(id)) {
      return new ResponseEntity(new Message("Error. No existe el usuario de Id: " + id), HttpStatus.NOT_FOUND);
    }

    this.personService.deletePerson(id);
    return new ResponseEntity(new Message("Ok. Usuario borrado: " + id), HttpStatus.OK);

  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<?> login(@RequestBody Person person) {

    Person loginPerson = this.personService.loginPerson(person.getEmail(), person.getPassword());

    //deleting the password for safety
    if (loginPerson != null) {
      loginPerson.clearPassword();
    }

    return (loginPerson != null
        ? new ResponseEntity<>(loginPerson, HttpStatus.OK)
        : new ResponseEntity<>(new Message("Error. Email o contraseña incorrectos."), HttpStatus.UNAUTHORIZED));

  }

}
