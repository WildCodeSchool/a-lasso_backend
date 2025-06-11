package com.back_alasso.Association;

import com.back_alasso.ActivityImage.ActivityImage;
import com.back_alasso.ActivityImage.ActivityImageRepository;
import com.back_alasso.Association.DTO.AssociationCardResponseDTO;
import com.back_alasso.AssociationFollower.AssociationFollower;
import com.back_alasso.AssociationFollower.AssociationFollowerRepository;
import com.back_alasso.AssociationImage.AssociationImageRepository;
import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Image.DTO.ImageResponseDTO;
import com.back_alasso.Image.ImageEnumType;
import com.back_alasso.Image.ImageMapper;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.Voluntary.VoluntaryRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssociationService {

  private final AssociationRepository associationRepository;
  private final AssociationFollowerRepository associationFollowerRepository;
  private final VoluntaryRepository voluntaryRepository;
  private final AssociationImageRepository associationImageRepository;
  private final AssociationCardResponseMapper associationCardResponseMapper;
  private final ImageMapper imageMapper;
  private final ActivityImageRepository activityImageRepository;

  public AssociationService(
    AssociationRepository associationRepository,
    VoluntaryRepository voluntaryRepository,
    AssociationFollowerRepository associationFollowerRepository,
    AssociationImageRepository associationImageRepository,
    AssociationCardResponseMapper associationCardResponseMapper,
    ImageMapper imageMapper,
    ActivityImageRepository activityImageRepository
  ) {
    this.associationRepository = associationRepository;
    this.associationFollowerRepository = associationFollowerRepository;
    this.voluntaryRepository = voluntaryRepository;
    this.associationImageRepository = associationImageRepository;
    this.associationCardResponseMapper = associationCardResponseMapper;
    this.imageMapper = imageMapper;
    this.activityImageRepository = activityImageRepository;
  }

  public AssociationCardResponseDTO getAssociation(UUID id, UUID authenticatedUserId) {
    Association association = associationRepository.findById(id).orElse(null);
    return associationCardResponseMapper.fromEntityToDTO(association, authenticatedUserId);
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

  public List<ImageResponseDTO> getExistingActivityPictures(UUID associationId, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);

    Page<ActivityImage> pagedImages = activityImageRepository.findByActivity_Association_Id(associationId, pageable);

    List<UUID> imageIds = pagedImages.getContent().stream().map(activityImage -> activityImage.getImage().getId()).toList();

    return imageMapper.toResponseDTOs(imageIds, ImageEnumType.ACTIVITY);
  }
}
