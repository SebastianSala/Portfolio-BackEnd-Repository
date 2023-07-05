package com.portfolio.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.portfolio.api.security.jwt.AuthEntryPointJwt;
import com.portfolio.api.security.jwt.AuthTokenFilter;
import com.portfolio.api.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)

//@ComponentScan("com.portfolio.api.security")
public class WebSecurityConfig {

  @Value("${api.corsOrigins}")
  private String[] corsUrls;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

//  @Autowired(required = false)
  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(this.userDetailsService);
    authProvider.setPasswordEncoder(this.passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(this.unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth
            -> auth.requestMatchers("/auth/**").permitAll()
            // Allowing Swagger docs
            .requestMatchers("/api-docs/**", "/swagger-ui/**").permitAll()
            // Allowing backend healthCheck from Render.com hosting platform
            .requestMatchers("/").permitAll()
            // Allowing backend healthCheck from Frontend 
            .requestMatchers("/check").permitAll()
            // Allowing entities public methods for everyone to see the profile without editing it
            .requestMatchers("/person/**").permitAll()
            .requestMatchers("/skill/**").permitAll()
            .requestMatchers("/education/**").permitAll()
            .requestMatchers("/experience/**").permitAll()
            .requestMatchers("/network/**").permitAll()
            .requestMatchers("/project/**").permitAll()
            .anyRequest().authenticated()
        );

    httpSecurity.authenticationProvider(this.authenticationProvider());

    httpSecurity.addFilterBefore(this.authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  @Bean
  public WebMvcConfigurer corsBean() {

    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins(WebSecurityConfig.this.corsUrls)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .maxAge(3600);
      }
    };

  }

}
