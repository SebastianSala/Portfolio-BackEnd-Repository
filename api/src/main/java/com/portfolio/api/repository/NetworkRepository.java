package com.portfolio.api.repository;

import com.portfolio.api.entity.Network;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkRepository extends JpaRepository<Network, Long> {

  List<Network> findByPersonId(Long networkId);

  List<Network> findByPersonEmail(String personEmail);

  Network findByPersonIdAndId(Long personId, Long networkId);

  boolean existsByPersonIdAndId(Long personId, Long networkId);

  boolean existsByPersonId(Long personId);

  @Transactional
  void deleteByPersonId(Long personId);

}
