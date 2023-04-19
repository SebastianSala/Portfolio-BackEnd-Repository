package com.portfolio.api.service;

import com.portfolio.api.entity.Person;
import java.util.List;
import java.util.Optional;

public interface IPersonService {
  
  public List<Person> showPersons();
  
  public Optional<Person> findPerson(Long id);
  
  public void deletePerson(Long id);
  
  public void createPerson(Person person);
  
  public void editPerson(Person person);
  
}
