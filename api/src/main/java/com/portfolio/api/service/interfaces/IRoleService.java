package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.ERole;
import com.portfolio.api.entity.Role;
import java.util.Optional;

public interface IRoleService {

  public Optional<Role> findByName(ERole name);

}
