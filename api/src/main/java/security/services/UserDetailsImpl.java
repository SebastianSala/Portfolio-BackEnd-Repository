package security.services;

import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
  
  private static final long serialVersionUID = 11L;
  
  private Long id;
  private String username;
  
}
