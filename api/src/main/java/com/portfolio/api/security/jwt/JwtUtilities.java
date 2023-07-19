package com.portfolio.api.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.security.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class JwtUtilities {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtilities.class);

  @Value("${api.app.jwtSecret}")
  private String jwtSecret;

  @Value("${api.app.jwtExpiration}")
  private Long jwtExpiration;

  @Value("${api.app.jwtCookieName}")
  private String jwtCookieName;

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret));
  }

  public String generateTokenFromEmail(String email) {
    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + this.jwtExpiration))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

// deprecated
//  public String getEmailFromJwtToken(String jwt) {
//    return Jwts.parserBuilder().setSigningKey(key()).build()
//        .parseClaimsJws(jwt).getBody().getSubject();
//  }
  public String getEmailFromToken(String jwt) {
    return this.getClaims(jwt, Claims::getSubject);
  }

  public String getToken(String email) {
    return getToken(new HashMap<>(), email);
  }

  private String getToken(Map<String, Object> extraClaims, String email) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(this.key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String jwt, String email) {
    final String theEmail = email;
    return (theEmail.equals(email) && !this.isTokenExpired(jwt));
  }

  private Claims getAllClaims(String jwt) {
    return Jwts
        .parserBuilder()
        .setSigningKey(this.key())
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }

  public <T> T getClaims(String jwt, Function<Claims, T> claimsResolver) {
    final Claims claims = this.getAllClaims(jwt);
    return claimsResolver.apply(claims);
  }

  private Date getExpiration(String jwt) {
    return getClaims(jwt, Claims::getExpiration);
  }

  private boolean isTokenExpired(String jwt) {
    return getExpiration(jwt).before(new Date());
  }

}
