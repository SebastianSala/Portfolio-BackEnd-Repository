package com.portfolio.api.controller;

import com.portfolio.api.entity.Skill;
import com.portfolio.api.service.SkillService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("skill")
@CrossOrigin(origins = "http://localhost:4200")
public class SkillController {

  @Autowired
  SkillService skillService;

  @PostMapping("/create")
  public ResponseEntity createSkill(@RequestBody Skill skill) {
    this.skillService.createSkill(skill);
    return ResponseEntity.ok("Se creo la habilidad correctamente" + skill.getId());
  }

  @PutMapping("/edit")
  public ResponseEntity editSkill(@RequestBody Skill skill) {
    this.skillService.editSkill(skill);
    return ResponseEntity.ok("Habilidad actualizada" + skill.getId());
  }

  @GetMapping("/list")
  @ResponseBody
  public List<Skill> listSkills() {
    return this.skillService.listSkills();
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public Optional<Skill> findSkill(@PathVariable Long id) {
    return this.skillService.findSkill(id);
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteSkill(@RequestParam("id") Long id) {
    this.skillService.deleteSkill(id);
    return ResponseEntity.ok("Habilidad " + id + " eliminada");
  }

}
