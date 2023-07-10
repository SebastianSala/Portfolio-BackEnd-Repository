package com.portfolio.api.controller;

import com.portfolio.api.dto.response.MessageResponse;
import com.portfolio.api.dto.response.PersonDTO;
import com.portfolio.api.entity.ERole;
import com.portfolio.api.entity.Person;
import com.portfolio.api.entity.Role;
import com.portfolio.api.service.PersonService;
import com.portfolio.api.service.RoleService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/person")
public class PersonController {

  @Autowired
  PersonService personService;

  @Autowired
  RoleService roleService;

  // public access allowed
//  @PostMapping("/create")
//  public ResponseEntity<?> createPerson(@RequestBody Person person) {
//
//    if (this.personService.existsByEmail(person.getEmail())) {
//      return new ResponseEntity<>(new MessageResponse("Error. Ese email ya existe, ingrese uno distinto"), HttpStatus.BAD_REQUEST);
//    }
//
//    this.personService.createPerson(person);
//    return new ResponseEntity<>(new MessageResponse("Ok. Usuario " + person.getName() + " creado con exito:  " + person.getEmail()), HttpStatus.OK);
//
//  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/edit/{id}")
  //http://localhost:8080/person/edit/1
  public ResponseEntity<?> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {

    if (person.getId() != id) {
      return new ResponseEntity<>(new MessageResponse("Error. No hay coincidencia entre usuario de Id " + person.getId() + " y Id de Request: " + id), HttpStatus.BAD_REQUEST);
    }

    if (this.personService.existsById(id)) {

      //Checks if a person with the same mail already exist, excluding the current one by its "id" that will have the same email of course
      if (this.personService.existsByEmailAndIdNot(person.getEmail(), id)) {
        return new ResponseEntity<>(new MessageResponse("Error. El email ya existe, ingrese uno distinto."), HttpStatus.BAD_REQUEST);
      }
      //Copy the curent password to not overwrite it with the blank password comming from the frontend
      //Since that data comes in blank becose is not managed in the frontend
      Person tempPerson = this.personService.findPerson(id);
      person.setPassword(tempPerson.getPassword());

      //Copy the admin role becose again, the data comes in blank from the frontend
      Set<Role> adminRole = new HashSet<>();
      Role role = this.roleService.findByName(ERole.ROLE_ADMIN)
          .orElseThrow(() -> new RuntimeException("Error. Rol sin definir"));
      adminRole.add(role);

      person.setRoles(adminRole);
      this.personService.editPerson(person);

      return new ResponseEntity<>(new MessageResponse("Ok. Usuario actualizado: " + person.getEmail()), HttpStatus.OK);

    } else {
      return new ResponseEntity<>(new MessageResponse("Error. No existe usuario para editar de Id: " + id), HttpStatus.NOT_FOUND);
    }

  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/list")
  @ResponseBody
  public ResponseEntity<?> listPersons() {
    System.out.println("--list person 1");
    List<Person> persons = this.personService.listPersons();
    System.out.println("--list person 2");

    if (persons.isEmpty()) {
      return new ResponseEntity<>(new MessageResponse("Error. No existen Usuarios. Cree una cuenta nueva"), HttpStatus.NOT_FOUND);
    }
    
    System.out.println("--list person 3");

    persons.forEach(Person::clearPassword);
    System.out.println("--list person 4");
    return new ResponseEntity<>(persons, HttpStatus.OK);

  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/list/{id}")
  @ResponseBody
  public ResponseEntity<?> findPerson(@PathVariable("id") Long id) {

    Person thePerson = this.personService.findPerson(id);

    if (thePerson == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe usuario con id: " + id), HttpStatus.NOT_FOUND);

    } else {
      // deprecated      
      //deleting the password for safety      
      //thePerson.clearPassword();
      PersonDTO personDTO = new PersonDTO(thePerson);

//      return new ResponseEntity<>(personDTO, HttpStatus.OK);
      return new ResponseEntity<>(thePerson, HttpStatus.OK);
    }

  }

  // public access allowed
  @GetMapping("/list/person")
  @ResponseBody
  public ResponseEntity<?> findPersonByEmail(@RequestParam("email") String email) {

    System.out.println("email person 1");
    Person thePerson = this.personService.finPersonByEmail(email);
    System.out.println("email person 2");
    PersonDTO personDTO = new PersonDTO(thePerson);
    System.out.println("email person 3");

//     deprecated      
//    deleting the password for safety
//    if (thePerson != null) {
//      //thePerson.clearPassword();
//    }
    return (thePerson != null
        ? new ResponseEntity<>(personDTO, HttpStatus.OK)
        : new ResponseEntity<>(new MessageResponse("Error. No existe usuario con el email: " + email), HttpStatus.NOT_FOUND));

  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete")
  public ResponseEntity<?> deletePersonById(@RequestParam("id") Long id) {

    if (!personService.existsById(id)) {
      return new ResponseEntity(new MessageResponse("Error. No existe el usuario de Id: " + id), HttpStatus.NOT_FOUND);
    }

    this.personService.deletePerson(id);
    return new ResponseEntity(new MessageResponse("Ok. Usuario borrado: " + id), HttpStatus.OK);

  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<?> login(@RequestBody Person person) {

    PersonDTO loginPerson = this.personService.loginPerson(person.getEmail(), person.getPassword());

    // deprecated, using DTO layer to not return password
    // deleting the password for safety
    //    if (loginPerson != null) {
    //      loginPerson.clearPassword();
    //    }
    return (loginPerson != null
        ? new ResponseEntity<>(loginPerson, HttpStatus.OK)
        : new ResponseEntity<>(new MessageResponse("Error. Email o contraseña incorrectos."), HttpStatus.UNAUTHORIZED));

  }

}
