package com.back_alasso.Authentication;

import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.User.User;
import com.back_alasso.User.UserRepository;
import com.back_alasso.security.JwtService;
import java.util.UUID;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;

  public AuthService(JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  public String authenticate(String email, String password) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    return jwtService.generateToken((UserDetails) authentication.getPrincipal());
  }

  public UUID getUserIdFromToken(String token) {
    String email = jwtService.extractEmail(token);
    User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    return user.getId();
  }
}
