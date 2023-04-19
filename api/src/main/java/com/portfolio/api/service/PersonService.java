package com.portfolio.api.service;

import com.portfolio.api.entity.Person;
import com.portfolio.api.repository.PersonRepository;
import jakarta.transaction.Transactional;
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
  public List<Person> showPersons() {
    return personRepository.findAll();
  }

  @Override
  public Optional<Person> findPerson(Long id) {
    return personRepository.findById(id);
  }

  @Override
  public void deletePerson(Long id) {
    personRepository.deleteById(id);
  }

  @Override
  public void createPerson(Person person) {
    personRepository.save(person);
  }

  @Override
  public void editPerson(Person person) {
    personRepository.save(person);
  }

}
