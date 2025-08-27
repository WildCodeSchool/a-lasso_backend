package com.back_alasso.features.Voluntary;

import com.back_alasso.features.User.UserService;
import com.back_alasso.features.Voluntary.DTO.VoluntaryLoginResponseDTO;
import com.back_alasso.features.Voluntary.DTO.VoluntaryUpdateRequestDTO;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/voluntary")
@RequiredArgsConstructor
public class VoluntaryController {

  private final VoluntaryService voluntaryService;
  private final VoluntaryLoginResponseMapper loginMapper;
  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<VoluntaryLoginResponseDTO> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    Voluntary voluntary = voluntaryService.findById(authenticatedUserId);
    VoluntaryLoginResponseDTO dto = loginMapper.fromEntityToDTO(voluntary);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/me")
  public ResponseEntity<Void> updateMyProfile(@Valid @RequestBody VoluntaryUpdateRequestDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    voluntaryService.updateVoluntary(authenticatedUserId, dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/me/avatar")
  public ResponseEntity<Void> uploadMyAvatar(@RequestParam("avatar") MultipartFile avatar, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    voluntaryService.uploadAvatar(authenticatedUserId, avatar);
    return ResponseEntity.ok().build();
  }
}
