package com.back_alasso.Association;

import com.back_alasso.User.UserService;
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
  public ResponseEntity<AssociationCardDTO> getAssociationCard(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    AssociationCardDTO associationCard = associationService.getAssociation(id, authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(associationCard);
  }

  @PatchMapping("/{associationId}/updateFollow")
  public ResponseEntity<Boolean> putUpdateFollowStatus(
    @PathVariable UUID associationId,
    @RequestBody UpdateFollowRequestDTO request,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    UUID authenticatedUserId = userService.getAuthenticatedUserId(userDetails);
    boolean updatedFollowStatus = associationService.updateFollowStatus(associationId, request.isFollow(), authenticatedUserId);
    return ResponseEntity.status(HttpStatus.OK).body(updatedFollowStatus);
  }
}
