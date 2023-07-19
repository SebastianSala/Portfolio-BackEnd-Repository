package com.portfolio.api.controller;

import com.portfolio.api.dto.request.LoginRequest;
import com.portfolio.api.dto.request.SignupRequest;
import com.portfolio.api.dto.response.JwtAndUserResponse;
import com.portfolio.api.dto.response.MessageResponse;
import com.portfolio.api.dto.response.PersonDTO;
import com.portfolio.api.entity.ERole;
import com.portfolio.api.entity.Person;
import com.portfolio.api.entity.Role;
import com.portfolio.api.service.PersonService;
import com.portfolio.api.service.RoleService;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.portfolio.api.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

//for Angular Client when working with cookies (withCredentials)
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/auth")
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

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {

    // Using Email instead of Username
    Authentication authentication = this.authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = this.jwtUtils.generateTokenFromEmail(loginRequest.getEmail());
    System.out.println(jwt);

    Person person = new Person(
        userDetails.getId(),
        userDetails.getName(),
        userDetails.getTitle(),
        userDetails.getEmail(),
        "",
        userDetails.getLocation(),
        userDetails.getAboutMe(),
        userDetails.getImgUrl(),
        userDetails.getImgBackUrl(),
        userDetails.getWebUrl()
    );

    Set<Role> adminRole = new HashSet<>();
    Role role = this.roleService.findByName(ERole.ROLE_ADMIN)
        .orElseThrow(() -> new RuntimeException("Error. Rol sin definir"));
    adminRole.add(role);

    person.setRoles(adminRole);
    PersonDTO personDTO = new PersonDTO(person);

    System.out.println("the personDTO: ");
    System.out.println(personDTO.toString());

    return ResponseEntity.ok(new JwtAndUserResponse(jwt, "Bearer", personDTO));

  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

    // if a person with the same email already exists, then dont create it
    if (this.personService.existsByEmail(signupRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error. Ese email ya existe, ingrese uno distinto"));
    }

    // if the mail is not already taken, then create the new user
    Person person = new Person(
        // dummy id number for creation
        -1L,
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
          .orElseThrow(() -> new RuntimeException("Error. Falta definir Rol en la base de datos. Definirlo antes de crear un usuario. Mas info en la wiki del ropositorio en Github."));

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

    return ResponseEntity.ok(new MessageResponse("Ok. Usuario creado con exito"));
//    return ResponseEntity.ok(AuthResponse.builder()
//        .token(this.jwtUtils.getToken(signupRequest.getEmail()))
//        .build());
  }

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
