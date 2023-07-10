package com.portfolio.api.controller;

import com.portfolio.api.dto.request.LoginRequest;
import com.portfolio.api.dto.request.SignupRequest;
import com.portfolio.api.dto.response.AuthResponse;
import com.portfolio.api.dto.response.MessageResponse;
import com.portfolio.api.dto.response.PersonDTO;
import com.portfolio.api.entity.ERole;
import com.portfolio.api.entity.Person;
import com.portfolio.api.entity.Role;
import com.portfolio.api.service.PersonService;
import com.portfolio.api.service.RoleService;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.portfolio.api.security.jwt.JwtUtilities;
import com.portfolio.api.security.services.AuthService;
import com.portfolio.api.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("auth")
//@RequiredArgsConstructor
//@RequiredArgsConstructor //posible problems with autowired?
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  PersonService personService;

  @Autowired
  RoleService roleService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
//  @Autowired(required = false)
  JwtUtilities jwtUtils;

  @Autowired
  AuthService authService;

  @PostMapping("/login")
//  public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
  public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {

    System.out.println("---------- is jwt null? 0");

    // Using Email instead of Username
    Authentication authentication = this.authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

//        SecurityContextHolder.getContext().setAuthentication(authentication);
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

//    String[] jwt = AuthResponse.builder().token(this.jwtUtils.getToken(loginRequest.getEmail())).build()).toString();
    String jwt = this.jwtUtils.getToken(loginRequest.getEmail());

    return ResponseEntity.ok(jwt);
//        AuthResponse.builder()
//            .token(this.jwtUtils.getToken(loginRequest.getEmail())).build());
//        .header(HttpHeaders.AUTHORIZATION, jwt)
//        .body(new PersonDTO(
//            userDetails.getId(),
//            userDetails.getName(),
//            userDetails.getTitle(),
//            userDetails.getEmail(),
//            userDetails.getLocation(),
//            userDetails.getAboutMe(),
//            userDetails.getImgUrl(),
//            userDetails.getImgBackUrl(),
//            userDetails.getWebUrl(),
//            roles));

  }
//  @PostMapping("/login")
//  public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//    // Using Email instead of Username
//    Authentication authentication = this.authenticationManager
//        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//    ResponseCookie jwtCookie = this.jwtUtils.generateJwtCookie(userDetails);
//
//    List<String> roles = userDetails.getAuthorities().stream()
//        .map(item -> item.getAuthority())
//        .collect(Collectors.toList());
//
//    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//        .body(new PersonDTO(
//            userDetails.getId(),
//            userDetails.getName(),
//            userDetails.getTitle(),
//            userDetails.getEmail(),
//            userDetails.getLocation(),
//            userDetails.getAboutMe(),
//            userDetails.getImgUrl(),
//            userDetails.getImgBackUrl(),
//            userDetails.getWebUrl(),
//            roles));
//
//  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

    // if a person with the same email already exists, then dont create it
    if (this.personService.existsByEmail(signupRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error. Ese email ya existe, ingrese uno distinto"));
    }

    // if the mail is not already taken, then create the new user
    Person person = new Person(
        signupRequest.getName(),
        "Título",
        signupRequest.getEmail(),
        this.passwordEncoder.encode(signupRequest.getPassword()),
        "Ubicación",
        "Acerca de",
        "Url de imagen de perfil",
        "Url de imagen de fondo",
        "Url de Web"
    );

    Set<String> strRoles = signupRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {

      // assign to every new user an ADMIN role so he can edit his profile
      Role adminRole = this.roleService.findByName(ERole.ROLE_ADMIN)
          .orElseThrow(() -> new RuntimeException("Error. Falta definir Rol en la base de datos"));

      roles.add(adminRole);

    } // only using admin role in this design but practicing with other roles aswell
    else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = this.roleService.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("1- Error. Rol sin definir"));
            roles.add(adminRole);

            break;
          case "editor":
            Role editorRole = this.roleService.findByName(ERole.ROLE_EDITOR)
                .orElseThrow(() -> new RuntimeException("2- Error. Rol sin definir"));
            roles.add(editorRole);

            break;
          default:
            Role userRole = this.roleService.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("3- Error. Rol sin definir"));

        }
      });
    }

    person.setRoles(roles);
    this.personService.createPerson(person);

//    return ResponseEntity.ok(new MessageResponse("Ok. Usuario creado con exito"));
    return ResponseEntity.ok(AuthResponse.builder()
        .token(this.jwtUtils.getToken(signupRequest.getEmail()))
        .build());

  }
//  @PostMapping("/register")
//  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
//
//    // if a person with the same email already exists, then dont create it
//    if (this.personService.existsByEmail(signupRequest.getEmail())) {
//      return ResponseEntity.badRequest().body(new MessageResponse("Error. Ese email ya existe, ingrese uno distinto"));
//    }
//
//    // if the mail is not already taken, then create the new user
//    Person person = new Person(
//        signupRequest.getName(),
//        "Título",
//        signupRequest.getEmail(),
//        this.passwordEncoder.encode(signupRequest.getPassword()),
//        "Ubicación",
//        "Acerca de",
//        "Url de imagen de perfil",
//        "Url de imagen de fondo",
//        "Url de Web"
//    );
//
//    Set<String> strRoles = signupRequest.getRole();
//    Set<Role> roles = new HashSet<>();
//
//    if (strRoles == null) {
//
//      // assign to every new user an ADMIN role so he can edit his profile
//      Role adminRole = this.roleService.findByName(ERole.ROLE_ADMIN)
//          .orElseThrow(() -> new RuntimeException("Error. Falta definir Rol en la base de datos"));
//
//      roles.add(adminRole);
//
//    } // only using admin role in this design but practicing with other roles aswell
//    else {
//      strRoles.forEach(role -> {
//        switch (role) {
//          case "admin":
//            Role adminRole = this.roleService.findByName(ERole.ROLE_ADMIN)
//                .orElseThrow(() -> new RuntimeException("1- Error. Rol sin definir"));
//            roles.add(adminRole);
//
//            break;
//          case "editor":
//            Role editorRole = this.roleService.findByName(ERole.ROLE_EDITOR)
//                .orElseThrow(() -> new RuntimeException("2- Error. Rol sin definir"));
//            roles.add(editorRole);
//
//            break;
//          default:
//            Role userRole = this.roleService.findByName(ERole.ROLE_USER)
//                .orElseThrow(() -> new RuntimeException("3- Error. Rol sin definir"));
//
//        }
//      });
//    }
//
//    person.setRoles(roles);
//    this.personService.createPerson(person);
//
//    return ResponseEntity.ok(new MessageResponse("Ok. Usuario creado con exito"));
//
//  }

//  @PostMapping("/logout")
//  public ResponseEntity<?> logoutUser() {
//
////    ResponseCookie cookie = this.jwtUtils.getCleanJwtCookie();
//
//    return ResponseEntity
//        .ok()
//        .header(HttpHeaders.SET_COOKIE, cookie.toString())
//        .body(new MessageResponse("Has cerrado sesión"));
//
//  }
}
