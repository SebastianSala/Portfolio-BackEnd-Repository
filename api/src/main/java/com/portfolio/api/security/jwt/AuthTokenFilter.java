package com.portfolio.api.security.jwt;

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

    try {
      String jwt = this.getTokenFromRequest(request);
      if (this.jwtUtilities.isTokenValid(jwt, this.jwtUtilities.getEmailFromToken(jwt))) {

        // using Email instead of Username
        String email = this.jwtUtilities.getEmailFromToken(jwt);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

      }
    } catch (Exception e) {
      logger.error("No se puede determinar la autenticación del usuario, revisar jwt: {}", e);
    }

    filterChain.doFilter(request, response);

  }

  private String getTokenFromRequest(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7, authHeader.length());
    }

    return null;
  }

}
