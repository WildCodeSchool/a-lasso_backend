package com.back_alasso.Association;

import com.back_alasso.Association.DTO.AssociationCardResponseDTO;
import com.back_alasso.Association.DTO.AssociationDescriptionRequestDTO;
import com.back_alasso.Association.DTO.AssociationGeneralInfoRequestDTO;
import com.back_alasso.Association.DTO.UpdateFollowRequestDTO;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.Statistic.StatisticDTO;
import com.back_alasso.User.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/association")
public class AssociationController {

  private final AssociationService associationService;
  private final UserService userService;

  public AssociationController(AssociationService associationService, UserService userService) {
    this.associationService = associationService;
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<AssociationCardResponseDTO> getAssociationCard(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    AssociationCardResponseDTO associationCard = associationService.getAssociation(id, authenticatedUserId);
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

    return ResponseEntity.ok(activitiesImages);
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

  @PutMapping("/{id}/general-info")
  public ResponseEntity<Void> updateGeneralInformation(
    @PathVariable UUID id,
    @Valid @RequestBody AssociationGeneralInfoRequestDTO associationGeneralInfoDTO,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    associationService.updateGeneralInfo(id, associationGeneralInfoDTO, authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/{id}/description")
  public ResponseEntity<Void> updateDescription(
    @PathVariable UUID id,
    @Valid @RequestBody AssociationDescriptionRequestDTO descriptionDTO,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    associationService.updateDescription(id, descriptionDTO.description(), authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/{id}/statistics")
  public ResponseEntity<Void> updateAssociationStatistics(
    @PathVariable UUID id,
    @Valid @RequestBody List<StatisticDTO> statistics,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    associationService.updateStatistics(id, statistics, authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
