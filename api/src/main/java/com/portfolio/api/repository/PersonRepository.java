package com.portfolio.api.repository;

import com.portfolio.api.entity.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  public List<Person> findByEmailAndPassword(String email, String password);

  public Person findByIdAndEmail(Long id, String email);
  
  public Person findByEmail(String email);
  
  public boolean existsByEmail(String email);
  
  public boolean existsByEmailAndIdNot(String email, Long id);

}
