package com.portfolio.api.controller;

import com.portfolio.api.dto.response.MessageResponse;
import com.portfolio.api.entity.Person;
import com.portfolio.api.entity.Network;
import com.portfolio.api.service.PersonService;
import com.portfolio.api.service.NetworkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class NetworkController {

  @Autowired
  NetworkService networkService;

  @Autowired
  PersonService personService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/person/{personId}/network")
  public ResponseEntity<?> createNetworkByPersonId(@PathVariable("personId") Long personId,
      @RequestBody Network networkRequest) {

    Person thePerson = personService.findPerson(personId);

    if (thePerson == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    } else {

      networkRequest.setPerson(thePerson);
      networkService.create(networkRequest);

      return new ResponseEntity<>(new MessageResponse("Ok. Red social creada: " + networkRequest.getName()), HttpStatus.CREATED);

    }

  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/person/{personId}/network/{networkId}")
  public ResponseEntity<?> updateNetworkByPersonIdByNetworkId(
      @PathVariable("personId") Long personId,
      @PathVariable("networkId") Long networkId,
      @RequestBody Network networkRequest
  ) {

    Person thePerson = personService.findPerson(personId);
    if (thePerson == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    Network theNetwork = networkService.findById(networkId);
    if (theNetwork == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No se encuentra la red de Id: " + networkId), HttpStatus.NOT_FOUND);
    }

    // Check to see if the url and the id of the network and person matches
    if ((theNetwork.getPerson().getId() != personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. La red existe, pero no pertenece a este usuario." + personId), HttpStatus.BAD_REQUEST);
    } else if ((networkRequest.getId() != networkId)) {
      return new ResponseEntity<>(new MessageResponse("Error. Intento de edición incorrecto, revisar parámetros."), HttpStatus.BAD_REQUEST);

      // if everything is ok, procede
    } else {
      networkRequest.setPerson(thePerson);
      networkService.edit(networkRequest);
      return new ResponseEntity<>(new MessageResponse("Ok. Red social actualizada!"), HttpStatus.CREATED);
    }

  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("person/{personId}/list")
  @ResponseBody
  public ResponseEntity<?> getNetworksByPersonId(@PathVariable("personId") Long personId) {

    if (!personService.existsById(personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    List<Network> theNetworks = networkService.listByPersonId(personId);

    if (theNetworks.isEmpty()) {
      return new ResponseEntity<>(new MessageResponse("Error. El usuario de Id: " + personId + " , no tiene redes que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    theNetworks.forEach(network -> network.getPerson().clearPassword());
    return new ResponseEntity<>(theNetworks, HttpStatus.OK);

  }

  // public access allowed
  @GetMapping("person/{personEmail}")
  @ResponseBody
  public ResponseEntity<?> getNetworksByPersonEmail(@PathVariable("personEmail") String personEmail) {

    if (!personService.existsByEmail(personEmail)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Email: " + personEmail), HttpStatus.NOT_FOUND);
    }

    List<Network> theNetworks = networkService.listByPersonEmail(personEmail);

    if (theNetworks.isEmpty()) {
      return new ResponseEntity<>(new MessageResponse("Error. El usuario de Email: " + personEmail + " , no tiene redes que mostrar"), HttpStatus.NOT_FOUND);
    }

    // clearing password for security before sending the data
    for (Network network : theNetworks) {
      Person person = network.getPerson();
      person.clearPassword();
    }

    return new ResponseEntity<>(theNetworks, HttpStatus.OK);

  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/person/{personId}/network/{networkId}")
  @ResponseBody
  public ResponseEntity<?> getNetworkByPersonIdByNetworkId(
      @PathVariable("personId") Long personId,
      @PathVariable("networkId") Long networkId) {

    Network theNetwork = this.networkService.findByPersonIdByNetworkId(personId, networkId);

    if (theNetwork == null) {
      return new ResponseEntity<>(new MessageResponse("Error. No se encuentra la red de Id: " + networkId), HttpStatus.NOT_FOUND);
    } else {
      theNetwork.getPerson().clearPassword();
      return new ResponseEntity<>(theNetwork, HttpStatus.OK);
    }

  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/person/delete")
  //http://localhost:8080/network/person/delete?personId=1&networkId=1
  public ResponseEntity<?> deleteNetworkByPersonIdByNetworkId(
      @RequestParam("personId") Long personId,
      @RequestParam("networkId") Long networkId
  ) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe el usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.networkService.existsById(networkId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe la red de Id: " + networkId), HttpStatus.NOT_FOUND);
    }

    if (!this.networkService.existsByPersonIdByNetworkId(personId, networkId)) {
      // if the project does not exist on the person, return not found
      return new ResponseEntity<>(new MessageResponse("Error. No existe la red de Id: " + networkId + " del usuario: " + personId), HttpStatus.NOT_FOUND);

    } else {
      // Everything ok, then return ok
      this.networkService.delete(networkId);

      return new ResponseEntity<>(new MessageResponse("Ok. Red borrada: " + networkId), HttpStatus.OK);
    }

  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/person/{personId}/delete")
  //http://localhost:8080/network/person/1/delete
  public ResponseEntity<?> deleteNetworksByPersonId(@PathVariable("personId") Long personId) {

    if (!this.personService.existsById(personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe la persona de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    if (!this.networkService.existsByPersonId(personId)) {
      return new ResponseEntity<>(new MessageResponse("Error. No existe la red del usuario de Id: " + personId), HttpStatus.NOT_FOUND);
    }

    this.networkService.deleteByPersonId(personId);
    return new ResponseEntity<>(new MessageResponse("Ok. Eliminados todas las redes del usuario: " + personId), HttpStatus.OK);

  }

}
