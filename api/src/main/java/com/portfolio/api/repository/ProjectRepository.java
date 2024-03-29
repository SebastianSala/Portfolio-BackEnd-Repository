package com.portfolio.api.repository;

import com.portfolio.api.entity.Project;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  List<Project> findByPersonId(Long projectId);
  
  List<Project> findByPersonEmail(String personEmail);

  Project findByPersonIdAndId(Long personId, Long projectId);

  boolean existsByPersonIdAndId(Long personId, Long projecetId);

  boolean existsByPersonId(Long personId);

  @Transactional
  void deleteByPersonId(Long personId);

}
