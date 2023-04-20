package com.portfolio.api.controller;

import com.portfolio.api.entity.Network;
import com.portfolio.api.service.NetworkService;
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
@RequestMapping("network")
@CrossOrigin(origins = "http://localhost:4200")
public class NetworkController {

  @Autowired
  NetworkService networkService;

  @PostMapping("/create")
  public ResponseEntity createNetwork(@RequestBody Network network) {
    this.networkService.createNetwork(network);
    String message = String.format("Network %d creada", network.getId());
    return ResponseEntity.ok(message);
  }

  @PutMapping("/edit")
  public ResponseEntity editNetwork(@RequestBody Network network) {
    this.networkService.editNetwork(network);
    return ResponseEntity.ok("Network editada" + network.getId());
  }

  @GetMapping("/list")
  @ResponseBody
  public ArrayList<Network> listNetworks() {
    return this.networkService.listNetworks();
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  public Optional<Network> findNetwork(@PathVariable Long id) {
    return this.networkService.findNetwork(id);
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteNetwork(@RequestParam("id") Long id) {
    this.networkService.deleteNetwork(id);
    return ResponseEntity.ok("Network " + id + " deleted");
  }

}
