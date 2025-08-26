package com.back_alasso.features.Authentication;

import com.back_alasso.features.Association.Association;
import com.back_alasso.features.Association.AssociationLoginResponseMapper;
import com.back_alasso.features.User.User;
import com.back_alasso.features.Voluntary.Voluntary;
import com.back_alasso.features.Voluntary.VoluntaryLoginResponseMapper;
import com.back_alasso.security.JwtService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final VoluntaryLoginResponseMapper voluntaryLoginResponseMapper;
  private final AssociationLoginResponseMapper associationLoginResponseMapper;

  public String generateToken(User user) {
    return jwtService.generateToken(user);
  }

  public String authenticate(String email, String password) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    return jwtService.generateToken((UserDetails) authentication.getPrincipal());
  }

  public void addUserToResponse(User user, Map<String, Object> response) {
    if (user instanceof Voluntary voluntary) {
      response.put("user", voluntaryLoginResponseMapper.fromEntityToDTO(voluntary));
    } else if (user instanceof Association association) {
      response.put("user", associationLoginResponseMapper.fromEntityToDTO(association));
    } else {
      throw new IllegalArgumentException("Unsupported user type");
    }
  }
}
