package com.portfolio.api.service;

import com.portfolio.api.service.interfaces.IPersonService;
import com.portfolio.api.entity.Person;
import com.portfolio.api.repository.PersonRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersonService implements IPersonService {

  @Autowired
  PersonRepository personRepository;

  @Override
  public void createPerson(Person person) {
    this.personRepository.save(person);
  }

  @Override
  public void editPerson(Person person) {
    this.personRepository.save(person);
  }

  @Override
  public ArrayList<Person> listPersons() {
    return (ArrayList<Person>) this.personRepository.findAll();
  }

  @Override
  public Optional<Person> findPerson(Long id) {
    return this.personRepository.findById(id);
  }

  @Override
  public void deletePerson(Long id) {
    this.personRepository.deleteById(id);
  }

  @Override
  public Person logInObject(String email, String password) {

    List<Person> person = this.personRepository.findByEmailAndPassword(email, password);

    if (!person.isEmpty()) {
      return person.get(0);
    } else {
      return null;
    }

  }

  @Override
  public String logInString(String email, String password) {

    List<Person> person = this.personRepository.findByEmailAndPassword(email, password);
    
    String login = "ERROR";

    if (!person.isEmpty()) {
      login = "OK";
    }
    
    return login;

  }

  @Override
  public Boolean existsById(Long id) {
    return this.personRepository.existsById(id);    
  }
  
  
  
}
