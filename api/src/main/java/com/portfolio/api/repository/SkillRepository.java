package com.portfolio.api.repository;

import com.portfolio.api.entity.Skill;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

  List<Skill> findByPersonId(Long skillId);

  List<Skill> findByPersonEmail(String personEmail);

  Skill findByPersonIdAndId(Long personId, Long skillId);

  boolean existsByPersonIdAndId(Long personId, Long skillId);

  boolean existsByPersonId(Long personId);

  @Transactional
  void deleteByPersonId(Long personId);

}
