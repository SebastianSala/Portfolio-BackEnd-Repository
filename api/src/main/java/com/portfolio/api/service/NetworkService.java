package com.portfolio.api.service;

import com.portfolio.api.entity.Network;
import com.portfolio.api.repository.NetworkRepository;
import com.portfolio.api.service.interfaces.INetworkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class NetworkService implements INetworkService {
  
  @Autowired
  NetworkRepository networkRepository;
  
    @Override
  public void create(Network network) {
    networkRepository.save(network);
  }

  @Override
  public void edit(Network network) {
    networkRepository.save(network);
  }

  @Override
  public Network findById(Long id) {
    return networkRepository.findById(id).orElse(null);
  }

  @Override
  public List<Network> listByPersonEmail(String personEmail) {
    return this.networkRepository.findByPersonEmail(personEmail);
  }

  @Override
  public List<Network> listByPersonId(Long personId) {
    return networkRepository.findByPersonId(personId);
  }

  @Override
  public Network findByPersonIdByNetworkId(Long personId, Long networkId) {
    return networkRepository.findByPersonIdAndId(personId, networkId);
  }

  @Override
  public boolean existsById(Long id) {
    return this.networkRepository.existsById(id);
  }

  @Override
  public boolean existsByPersonIdByNetworkId(Long personId, Long networkId) {
    return this.networkRepository.existsByPersonIdAndId(personId, networkId);
  }

  @Override
  public boolean existsByPersonId(Long personId) {
    return this.networkRepository.existsByPersonId(personId);
  }

  @Override
  public void delete(Long id) {
    networkRepository.deleteById(id);
  }

  @Override
  public void deleteByPersonId(Long personId) {
    this.networkRepository.deleteByPersonId(personId);
  }
  
}
