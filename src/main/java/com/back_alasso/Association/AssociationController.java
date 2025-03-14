package com.back_alasso.Association;

import com.back_alasso.Authentication.AuthService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/association")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class AssociationController {

  private final AssociationService associationService;
  private final AuthService authService;

  public AssociationController(AssociationService associationService, AuthService authService) {
    this.associationService = associationService;
    this.authService = authService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<AssociationCardDTO> getAssociationCard(@PathVariable UUID id) {
    AssociationCardDTO associationCard = associationService.getAssociation(id);
    return ResponseEntity.status(HttpStatus.OK).body(associationCard);
  }

  @PutMapping("/{associationId}/updateFollow")
  public ResponseEntity<Boolean> putUpdateFollowStatus(
    @PathVariable UUID associationId,
    @RequestBody boolean isFollow,
    @RequestHeader("Authorization") String token
  ) {
    UUID authenticatedUser = authService.getUserIdFromToken(token);
    boolean updatedFollowStatus = associationService.updateFollowStatus(associationId, isFollow, authenticatedUser);
    return ResponseEntity.status(HttpStatus.OK).body(updatedFollowStatus);
  }
}
