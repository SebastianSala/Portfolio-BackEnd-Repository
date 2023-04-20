package com.portfolio.api.service;

import com.portfolio.api.service.interfaces.IPersonService;
import com.portfolio.api.entity.Person;
import com.portfolio.api.repository.PersonRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
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
    return (ArrayList) this.personRepository.findAll();
  }

  @Override
  public Optional<Person> findPerson(Long id) {
    return this.personRepository.findById(id);
  }

  @Override
  public void deletePerson(Long id) {
    this.personRepository.deleteById(id);
  }

}
