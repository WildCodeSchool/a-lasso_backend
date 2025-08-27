package com.back_alasso.features.Association;

import com.back_alasso.features.Association.DTO.*;
import com.back_alasso.features.Image.DTO.ImageResponseDTO;
import com.back_alasso.features.Statistic.StatisticDTO;
import com.back_alasso.features.User.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/association")
@RequiredArgsConstructor
public class AssociationController {

  private final AssociationService associationService;
  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<AssociationGeneralInfoRequestDTO.AssociationLoginResponseDTO> getMyAssociation(
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    AssociationGeneralInfoRequestDTO.AssociationLoginResponseDTO association = associationService.getMyAssociation(authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(association);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AssociationCardResponseDTO> getAssociationCard(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    AssociationCardResponseDTO associationCard = associationService.getAssociationCard(id, authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(associationCard);
  }

  @GetMapping("/activities-images")
  public ResponseEntity<List<ImageResponseDTO>> getExistingActivityPictures(
    @AuthenticationPrincipal UserDetails userDetails,
    @RequestParam int offset,
    @RequestParam int limit
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    List<ImageResponseDTO> activitiesImages = associationService.getExistingActivityPictures(authenticatedUserId, offset, limit);
    return ResponseEntity.status(HttpStatus.OK).body(activitiesImages);
  }

  @PatchMapping("/{associationId}/updateFollow")
  public ResponseEntity<Boolean> putUpdateFollowStatus(
    @PathVariable UUID associationId,
    @Valid @RequestBody UpdateFollowRequestDTO request,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    boolean updatedFollowStatus = associationService.updateFollowStatus(associationId, request.isFollow(), authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(updatedFollowStatus);
  }

  @PutMapping("/me/general-info")
  public ResponseEntity<Void> updateGeneralInformation(
    @Valid @RequestBody AssociationGeneralInfoRequestDTO associationGeneralInfoDTO,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    associationService.updateGeneralInfo(authenticatedUserId, associationGeneralInfoDTO);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/me/description")
  public ResponseEntity<Void> updateDescription(
    @Valid @RequestBody AssociationDescriptionRequestDTO descriptionDTO,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    associationService.updateDescription(authenticatedUserId, descriptionDTO.description());
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/me/statistics")
  public ResponseEntity<Void> updateAssociationStatistics(
    @Valid @RequestBody List<StatisticDTO> statistics,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    associationService.updateStatistics(authenticatedUserId, statistics);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/me/logo")
  public ResponseEntity<Void> uploadMyLogo(@RequestParam("logo") MultipartFile logoFile, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    associationService.uploadLogo(authenticatedUserId, logoFile);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/cards")
  public ResponseEntity<List<AssociationCardResponseDTO>> getAssociationCards(
    @Valid @RequestBody GetAssociationCardsRequestDTO request,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    List<AssociationCardResponseDTO> associationCards = associationService.getAssociationCards(request.ids(), authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(associationCards);
  }
}
