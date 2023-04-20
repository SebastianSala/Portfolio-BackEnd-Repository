package com.portfolio.api.controller;

import com.portfolio.api.entity.Experience;
import com.portfolio.api.service.ExperienceService;
import java.util.ArrayList;
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
@RequestMapping("experience")
@CrossOrigin(origins = "http://localhost:4200")
public class ExperienceController {

  @Autowired
  ExperienceService experienceService;

  @PostMapping("/create")
  public ResponseEntity createExperience(@RequestBody Experience experience) {
    this.experienceService.createExperience(experience);
    String message = String.format("Experiencia %d creada!", experience.getId());
    return ResponseEntity.ok(message);
  }

  @PutMapping("/edit")
  public ResponseEntity editExperience(@RequestBody Experience experience) {
    this.experienceService.editExperience(experience);
    String message = String.format("Experiencia %d editada!", experience.getId());
    return ResponseEntity.ok(message);
  }

  @GetMapping("/list")
  @ResponseBody
  public ArrayList<Experience> listExperiences() {
    return this.experienceService.listExperiences();
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public Optional<Experience> findExperience(@PathVariable Long id) {
    return this.experienceService.findExperience(id);
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteExperience(@RequestParam("id") Long id) {
    this.experienceService.deleteExperience(id);
    String message = String.format("Experiencia %d eliminada!", id);
    return ResponseEntity.ok(message);
  }

}
