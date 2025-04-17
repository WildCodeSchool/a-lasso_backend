package com.back_alasso.Association;

import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationFollower.AssociationFollowerRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AssociationService {

  private final AssociationRepository associationRepository;
  private final AssociationFollowerRepository associationFollowerRepository;
  private final VoluntaryRepository voluntaryRepository;

  public AssociationService(
    AssociationRepository associationRepository,
    VoluntaryRepository voluntaryRepository,
    AssociationFollowerRepository associationFollowerRepository
  ) {
    this.associationRepository = associationRepository;
    this.associationFollowerRepository = associationFollowerRepository;
    this.voluntaryRepository = voluntaryRepository;
  }

  public AssociationCardDTO getAssociation(UUID id, UUID authenticatedUserId) {
    Association association = associationRepository.findById(id).orElse(null);
    return AssociationCardDTO.fromEntityToDTO(association, authenticatedUserId);
  }

  public Boolean updateFollowStatus(UUID associationId, boolean isFollow, UUID authenticatedUserId) {
    AssociationFollower associationfollower = associationFollowerRepository
      .findByVoluntary_idAndAssociation_id(authenticatedUserId, associationId)
      .orElse(null);

    if (associationfollower != null) {
      return updateFollowForNewAssociationFollower(associationfollower, isFollow);
    } else {
      return updateFollowForExistantAssociationFollower(associationId, isFollow, authenticatedUserId);
    }
  }

  private Boolean updateFollowForNewAssociationFollower(AssociationFollower associationfollower, boolean isFollow) {
    associationfollower.setFollow(isFollow);
    associationFollowerRepository.save(associationfollower);
    return isFollow;
  }

  private Boolean updateFollowForExistantAssociationFollower(UUID associationId, boolean isFollow, UUID authenticatedUser) {
    Voluntary voluntary = voluntaryRepository.findById(authenticatedUser).orElseThrow(() -> new ResourceNotFoundException("Voluntary not found"));
    Association association = associationRepository.findById(associationId).orElseThrow(() -> new ResourceNotFoundException("Association not found"));
    AssociationFollower newAssociationfollower = new AssociationFollower(false, isFollow, voluntary, association);
    associationFollowerRepository.save(newAssociationfollower);
    return isFollow;
  }
}
