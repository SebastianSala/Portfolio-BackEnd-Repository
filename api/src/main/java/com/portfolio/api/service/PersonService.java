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
//@Transactional
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
  public Person finPersonByIdAndEmail(Long id, String email) {
    return this.personRepository.findByIdAndEmail(id, email);
  }

  @Override
  public Person finPersonByEmail(String email) {
    return this.personRepository.findByEmail(email);
  }

  @Override
  public void deletePerson(Long id) {
    this.personRepository.deleteById(id);
  }

  @Override
  public Person loginPerson(String email, String password) {

    List<Person> person = this.personRepository.findByEmailAndPassword(email, password);

    if (!person.isEmpty()) {
      return person.get(0);
    }
    return null;

  }

  @Override
  public boolean existsById(Long id) {
    return this.personRepository.existsById(id);
  }
  
  @Override
  public boolean existsByEmail(String email) {
    return this.personRepository.existsByEmail(email);
  }
  
  @Override
  public boolean existsByEmailAndIdNot(String email, Long id) {
    return this.personRepository.existsByEmailAndIdNot(email, id);
  }

}
