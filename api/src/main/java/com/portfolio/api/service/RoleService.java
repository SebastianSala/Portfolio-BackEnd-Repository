package com.portfolio.api.service;

import com.portfolio.api.entity.ERole;
import com.portfolio.api.entity.Role;
import com.portfolio.api.repository.RoleRepository;
import com.portfolio.api.service.interfaces.IRoleService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

  @Autowired
  RoleRepository roleRepository;

  @Override
  public Optional<Role> findByName(ERole name) {
    return this.roleRepository.findByName(name);
  }

}
