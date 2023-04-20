package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Person;
import java.util.ArrayList;
import java.util.Optional;

public interface IPersonService {

  public void createPerson(Person person);

  public void editPerson(Person person);

  public ArrayList<Person> listPersons();

  public Optional<Person> findPerson(Long id);

  public void deletePerson(Long id);

}
