package com.portfolio.api.controller;

import com.portfolio.api.entity.Person;
import com.portfolio.api.service.PersonService;
import java.util.ArrayList;
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
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping("/create")
  public ResponseEntity createPerson(@RequestBody Person person) {
    this.personService.createPerson(person);
    return ResponseEntity.ok("Persona creada con exito = " + person.getId());
  }

  @PutMapping("/edit")
  public ResponseEntity editPerson(@RequestBody Person person) {
    this.personService.editPerson(person);
    return ResponseEntity.ok("edicion ok = " + person.getId());
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<String> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {

    Optional<Person> personData = this.personService.findPerson(id);

    if (personData.isPresent()) {
      Person updatedPerson = personData.get();
      //update only modified fields
//      updatedPerson.setName(person.getName());
      if (person.getName() != null) {
        updatedPerson.setName("TEXT! only name parameter implemented yet");//person.getName());        
      } else {
        updatedPerson.setName("NULL! only name parameter implemented yet");//person.getName());                
      }
      this.personService.editPerson(updatedPerson);
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/list")
  @ResponseBody
  public ArrayList<Person> listPersons() {
    return this.personService.listPersons();
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public Optional<Person> findPerson(@PathVariable Long id) {
    return this.personService.findPerson(id);
  }

  @DeleteMapping("/delete")
  public ResponseEntity deletePerson(@RequestParam("id") Long id) {
    this.personService.deletePerson(id);
    return ResponseEntity.ok("persona borrada = " + id);
  }

}
