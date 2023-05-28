package com.portfolio.api.repository;

import com.portfolio.api.entity.Education;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
  
  List<Education> findByPersonId(Long educationId);

  List<Education> findByPersonEmail(String personEmail);

  Education findByPersonIdAndId(Long personId, Long educationId);

  boolean existsByPersonIdAndId(Long personId, Long educationId);

  boolean existsByPersonId(Long personId);

  @Transactional
  void deleteByPersonId(Long personId);
}
