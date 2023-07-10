package com.portfolio.api.security.jwt;

import com.portfolio.api.security.services.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.portfolio.api.security.services.UserDetailsServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    System.out.println("---------- is jwt null? 1");

    String jwt = this.getTokenFromRequest(request);
    System.out.println("---------- is jwt null? 3");

    if (jwt == null) {

      System.out.println("---------- is jwt null?");

      filterChain.doFilter(request, response);
      return;
    }
    System.out.println("---------- is jwt null? 2");

    String email = this.jwtUtilities.getEmailFromToken(jwt);
    System.out.println("---------- is jwt null? 4");

    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    System.out.println("---------- is jwt null? 5");

      UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(email);
    System.out.println("---------- is jwt null? 6");
      if (this.jwtUtilities.isTokenValid(jwt, userDetails)) {
    System.out.println("---------- is jwt null? 7");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    System.out.println("---------- is jwt null? 8");
      }

    }

  }
//  @Override
//  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//      throws ServletException, IOException {
//
//    try {
////      String jwt = this.parseJwt(request);
////      if (jwt != null && this.jwtUtilities.validateJwtToken(jwt)) {
////
////        // using Email instead of Username
////        String email = this.jwtUtilities.getEmailFromJwtToken(jwt);
////        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
////
////        UsernamePasswordAuthenticationToken authenticationToken
////            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
////
////        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////
////        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
////
////      }
////    } catch (Exception e) {
////      logger.error("No se puede configurar la autenticación del usuario: {}", e);
////    }
//
//    filterChain.doFilter(request, response);
//
//  }

  private String getTokenFromRequest(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }

    return null;
  }

//  private String parseJwt(HttpServletRequest request) {
//    String jwt = jwtUtilities.getJwtFromCookies(request);
//    return jwt;
//  }
}
