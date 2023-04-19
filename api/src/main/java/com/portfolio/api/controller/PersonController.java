package com.portfolio.api.controller;

import com.portfolio.api.entity.Person;
import com.portfolio.api.service.PersonService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("person")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping("/create")
  public String createPerson(@RequestBody Person person) {
    personService.createPerson(person);
    return "Persona creada con exito = " + person;
  }
  
  @GetMapping("/show/{id}")
  @ResponseBody
  public Optional<Person> findPerson(@PathVariable Long id) {
    return personService.findPerson(id);
  }

  @GetMapping("/list")
  @ResponseBody
  public List<Person> showPersons() {
    return personService.showPersons();
  }

  @DeleteMapping("/delete/{id}")
  public String deletePerson(@PathVariable Long id) {
    personService.deletePerson(id);
    return "persona borrada = " + id;
  }
  
  @PutMapping("/edit")
  public String updatePerson(@RequestBody Person person) {
    personService.editPerson(person);
    return "edicion ok = " + person;
  }

}
