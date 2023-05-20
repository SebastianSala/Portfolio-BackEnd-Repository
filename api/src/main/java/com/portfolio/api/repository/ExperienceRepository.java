package com.portfolio.api.repository;

import com.portfolio.api.entity.Experience;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

  List<Experience> findByPersonId(Long experienceId);

  List<Experience> findByPersonEmail(String personEmail);

  Experience findByPersonIdAndId(Long personId, Long experienceId);

  boolean existsByPersonIdAndId(Long personId, Long experienceId);

  boolean existsByPersonId(Long personId);

  @Transactional
  void deleteByPersonId(Long personId);

}
