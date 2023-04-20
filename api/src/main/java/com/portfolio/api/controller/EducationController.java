package com.portfolio.api.controller;

import com.portfolio.api.entity.Education;
import com.portfolio.api.service.EducationService;
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
@RequestMapping("education")
@CrossOrigin(origins = "http://localhost:4200")
public class EducationController {

  @Autowired
  EducationService educationService;

  @PostMapping("/create")
  public ResponseEntity createEducation(@RequestBody Education education) {
    this.educationService.createEducation(education);
    return ResponseEntity.ok("Se creo el educación correctamente" + education.getId());
  }

  @PutMapping("/edit")
  public ResponseEntity editEducation(@RequestBody Education education) {
    this.educationService.editEducation(education);
    return ResponseEntity.ok("Educación actualizado" + education.getId());
  }

  @GetMapping("/list")
  @ResponseBody
  public List<Education> listEducations() {
    return this.educationService.listEducations();
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public Optional<Education> findEducation(@PathVariable Long id) {
    return this.educationService.findEducation(id);
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteEducation(@RequestParam("id") Long id) {
    this.educationService.deleteEducation(id);
    return ResponseEntity.ok("Educación " + id + " eliminado");
  }

}
