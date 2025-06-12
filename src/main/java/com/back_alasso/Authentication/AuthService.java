package com.back_alasso.Authentication;

import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationLoginResponseMapper;
import com.back_alasso.User.User;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryLoginResponseMapper;
import com.back_alasso.security.JwtService;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VoluntaryLoginResponseMapper voluntaryLoginResponseMapper;
    private final AssociationLoginResponseMapper associationLoginResponseMapper;

    public AuthService(
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            VoluntaryLoginResponseMapper voluntaryLoginResponseMapper,
            AssociationLoginResponseMapper associationLoginResponseMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.voluntaryLoginResponseMapper = voluntaryLoginResponseMapper;
        this.associationLoginResponseMapper = associationLoginResponseMapper;
    }

    public String authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return jwtService.generateToken((UserDetails) authentication.getPrincipal());
    }

    public boolean addUserToResponse(User user, Map<String, Object> response) {
        if (user instanceof Voluntary voluntary) {
            response.put("user", voluntaryLoginResponseMapper.fromEntityToDTO(voluntary));
            return true;
        } else if (user instanceof Association association) {
            response.put("user", associationLoginResponseMapper.fromEntityToDTO(association));
            return true;
        }
        return false;
    }
}
