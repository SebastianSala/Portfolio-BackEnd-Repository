package com.portfolio.api.security.services;

import com.portfolio.api.entity.Person;
import com.portfolio.api.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  PersonRepository personRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Person person;
    // loading by Email instead of username becose in the design, Email is the unique one be care about
    if (this.personRepository.findByEmail(email) != null) {
      person = this.personRepository.findByEmail(email);
    } else {
      throw (new UsernameNotFoundException("No se encuentra la persona de email: " + email));
    }

    return UserDetailsImpl.build(person);
  }

}
