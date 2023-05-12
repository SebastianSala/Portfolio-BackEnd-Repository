package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Person;
import java.util.ArrayList;

public interface IPersonService {

  public void createPerson(Person person);

  public void editPerson(Person person);

  public ArrayList<Person> listPersons();

//  public Optional<Person> findPerson(Long id);
  public Person findPerson(Long id);

  public Person finPersonByIdAndEmail(Long id, String email);
  
  public Person finPersonByEmail(String email);

  public void deletePerson(Long id);

  public Person loginPerson(String email, String password);

  public boolean existsById(Long id);
  
  public boolean existsByEmail(String email);
  
  public boolean existsByEmailAndIdNot(String email, Long id);

}
