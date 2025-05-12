package com.back_alasso.Voluntary;

import com.back_alasso.User.UserService;
import com.back_alasso.Voluntary.DTO.VoluntaryLoginResponseDTO;
import com.back_alasso.Voluntary.DTO.VoluntaryUpdateRequestDTO;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/voluntary")
public class VoluntaryController {

  private final VoluntaryService voluntaryService;
  private final VoluntaryLoginResponseMapper loginMapper;
  private final UserService userService;

  public VoluntaryController(VoluntaryService voluntaryService, VoluntaryLoginResponseMapper loginMapper, UserService userService) {
    this.voluntaryService = voluntaryService;
    this.loginMapper = loginMapper;
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<VoluntaryLoginResponseDTO> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    Voluntary voluntary = voluntaryService.findById(authenticatedUserId);
    VoluntaryLoginResponseDTO dto = loginMapper.fromEntityToDTO(voluntary);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/me")
  public ResponseEntity<Void> updateMyProfile(@RequestBody VoluntaryUpdateRequestDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    voluntaryService.updateVoluntary(authenticatedUserId, dto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/me/avatar")
  public ResponseEntity<Void> uploadMyAvatar(@RequestParam("avatar") MultipartFile avatar, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    voluntaryService.uploadAvatar(authenticatedUserId, avatar);
    return ResponseEntity.ok().build();
  }
}
