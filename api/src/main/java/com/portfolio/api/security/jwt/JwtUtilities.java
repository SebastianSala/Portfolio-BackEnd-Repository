package com.portfolio.api.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import com.portfolio.api.security.services.UserDetailsImpl;

import java.util.Date;
import java.security.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

@Component
public class JwtUtilities {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtilities.class);

  @Value("${api.app.jwtSecret}")
  private String jwtSecret;

  @Value("${api.app.jwtExpiration}")
  private Long jwtExpiration;

  @Value("${api.app.jwtCookieName}")
  private String jwtCookieName;

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, this.jwtCookieName);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userDetailPrincipal) {
//    String jwt = this.generateTokenFromUsername(userDetailPrincipal.getUsername());
//  generate using email instead of username    
    String jwt = this.generateTokenFromEmail(userDetailPrincipal.getEmail());

    ResponseCookie cookie = ResponseCookie.from(this.jwtCookieName, jwt).path("/").maxAge(this.jwtExpiration).httpOnly(true).secure(true).sameSite("None").domain("portfolio-backend-ss.onrender.com").build();
    return cookie;
  }

  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie.from(this.jwtSecret, null).path("/").build();
    return cookie;
  }

  public String getEmailFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {

    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;

    } catch (MalformedJwtException e) {
      this.logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      this.logger.error("Jwt token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      this.logger.error("Jwt token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      this.logger.error("Jwt claims string is empty: {}", e.getMessage());
    }

    return false;

  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret));
  }

  public String generateTokenFromEmail(String email) {
    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + this.jwtExpiration))
        .signWith(key())
        .compact();
  }

}