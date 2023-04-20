package com.portfolio.api.service.interfaces;

import com.portfolio.api.entity.Network;
import java.util.ArrayList;
import java.util.Optional;

public interface INetworkService {
  
  public void createNetwork(Network network);
  
  public void editNetwork(Network network);
  
  public ArrayList<Network> listNetworks();
  
  public Optional<Network> findNetwork(Long id);
  
  public void deleteNetwork(Long id);
  
}
