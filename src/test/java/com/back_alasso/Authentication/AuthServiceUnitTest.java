package com.back_alasso.Authentication;

import com.back_alasso.Association.Association;
import com.back_alasso.Association.AssociationLoginResponseMapper;
import com.back_alasso.Association.DTO.AssociationLoginResponseDTO;
import com.back_alasso.User.User;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.DTO.VoluntaryLoginResponseDTO;
import com.back_alasso.Voluntary.VoluntaryLoginResponseMapper;
import com.back_alasso.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceUnitTest {

    AuthService authService;
    Map<String, Object> response;

    JwtService jwtServiceMock = mock(JwtService.class);
    AuthenticationManager authenticationManagerMock = mock(AuthenticationManager.class);
    VoluntaryLoginResponseMapper voluntaryLoginResponseMapperMock = mock(VoluntaryLoginResponseMapper.class);
    AssociationLoginResponseMapper associationLoginResponseMapperMock = mock(AssociationLoginResponseMapper.class);

    @BeforeEach
    void setUp() {
        response = new HashMap<>();
        authService = new AuthService(
                jwtServiceMock,
                authenticationManagerMock,
                voluntaryLoginResponseMapperMock,
                associationLoginResponseMapperMock
        );
    }

    @Test
    void addUserToResponse_shouldAddVoluntaryDTO() {
        Voluntary voluntaryMock = mock(Voluntary.class);
        VoluntaryLoginResponseDTO expectedDTO = mock(VoluntaryLoginResponseDTO.class);

        when(voluntaryLoginResponseMapperMock.fromEntityToDTO(voluntaryMock)).thenReturn(expectedDTO);

        authService.addUserToResponse(voluntaryMock, response);

        assertEquals(expectedDTO, response.get("user"));
    }

    @Test
    void addUserToResponse_shouldAddAssociationDTO() {
        Association associationMock = mock(Association.class);
        AssociationLoginResponseDTO expectedDTO = mock(AssociationLoginResponseDTO.class);

        when(associationLoginResponseMapperMock.fromEntityToDTO(associationMock)).thenReturn(expectedDTO);

        authService.addUserToResponse(associationMock, response);

        assertEquals(expectedDTO, response.get("user"));
    }

    @Test
    void addUserToResponse_shouldThrowExceptionForUnknownUserType() {
        User unknownUser = mock(User.class);

        assertThrows(IllegalArgumentException.class, () ->
                authService.addUserToResponse(unknownUser, response)
        );
    }
}
