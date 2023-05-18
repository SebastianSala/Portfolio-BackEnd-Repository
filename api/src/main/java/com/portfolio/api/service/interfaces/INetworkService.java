package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Network;
import java.util.List;

public interface INetworkService {
  
  public void create(Network network);

  public void edit(Network network);

  public List<Network> listByPersonEmail(String personEmail);

  public List<Network> listByPersonId(Long personId);

  public Network findById(Long id);

  public Network findByPersonIdByNetworkId(Long personId, Long networkId);

  public boolean existsByPersonIdByNetworkId(Long personId, Long networkId);

  public boolean existsById(Long id);

  public boolean existsByPersonId(Long personId);

  public void delete(Long id);

  public void deleteByPersonId(Long personId);
  
}
