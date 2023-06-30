package com.portfolio.api.service;

import com.portfolio.api.dto.response.PersonDTO;
import com.portfolio.api.service.interfaces.IPersonService;
import com.portfolio.api.entity.Person;
import com.portfolio.api.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// Not using transactional in tradeoff for performance
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

//  @Override
//  public Optional<Person> findPerson(Long id) {
//    return this.personRepository.findById(id);
//  }
  @Override
  public Person findPerson(Long id) {
    return this.personRepository.findById(id).orElse(null);
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
  public PersonDTO loginPerson(String email, String password) {

    List<Person> person = this.personRepository.findByEmailAndPassword(email, password);

    if (!person.isEmpty()) {
      Person tempPerson = person.get(0);
      
      List<String> tempListRole = new ArrayList<>();
      tempListRole.add("ROLE_USER");
      
      PersonDTO personDTO = new PersonDTO(tempPerson.getId(), tempPerson.getName(), tempPerson.getTitle(),
          tempPerson.getEmail(), tempPerson.getLocation(), tempPerson.getAboutMe(), tempPerson.getImgUrl(),
          tempPerson.getImgBackUrl(), tempPerson.getWebUrl(), tempListRole);

      return personDTO;
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
