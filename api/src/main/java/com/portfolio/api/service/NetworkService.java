package com.portfolio.api.service;

import com.portfolio.api.entity.Network;
import com.portfolio.api.repository.NetworkRepository;
import com.portfolio.api.service.interfaces.INetworkService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NetworkService implements INetworkService {
  
  @Autowired
  NetworkRepository networkRepository;
  
  @Override
  public void createNetwork(Network network) {
    this.networkRepository.save(network);
  }
  
  @Override
  public void editNetwork(Network network) {
    this.networkRepository.save(network);
  }
  
  @Override
  public ArrayList<Network> listNetworks() {
    return (ArrayList<Network>) this.networkRepository.findAll();
  }
  
  @Override
  public Optional<Network> findNetwork(Long id) {
    return this.networkRepository.findById(id);
  }
  
  @Override
  public void deleteNetwork(Long id) {
    this.networkRepository.deleteById(id);
  }
  
}
