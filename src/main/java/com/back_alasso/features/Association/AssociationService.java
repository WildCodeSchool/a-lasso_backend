package com.back_alasso.features.Association;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.ActivityImage.ActivityImageRepository;
import com.back_alasso.features.Association.DTO.AssociationCardResponseDTO;
import com.back_alasso.features.Association.DTO.AssociationGeneralInfoRequestDTO;
import com.back_alasso.features.AssociationFollower.AssociationFollower;
import com.back_alasso.features.AssociationFollower.AssociationFollowerRepository;
import com.back_alasso.features.AssociationImage.AssociationImage;
import com.back_alasso.features.Image.*;
import com.back_alasso.features.Image.DTO.ImageResponseDTO;
import com.back_alasso.features.Statistic.Statistic;
import com.back_alasso.features.Statistic.StatisticDTO;
import com.back_alasso.features.Voluntary.Voluntary;
import com.back_alasso.features.Voluntary.VoluntaryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AssociationService {

  private final AssociationRepository associationRepository;
  private final AssociationFollowerRepository associationFollowerRepository;
  private final VoluntaryRepository voluntaryRepository;
  private final AssociationCardResponseMapper associationCardResponseMapper;
  private final ImageMapper imageMapper;
  private final ActivityImageRepository activityImageRepository;
  private final ImageRepository imageRepository;
  private final ImageService imageService;
  private final AssociationLoginResponseMapper associationLoginResponseMapper;

  private Association getAuthenticatedAssociationById(UUID associationId) {
    Association association = getAssociationById(associationId);

    return association;
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

  public Association getAssociationById(UUID associationId) {
    return associationRepository.findById(associationId).orElseThrow(() -> new ResourceNotFoundException("Association not found"));
  }

  @Transactional
  public void updateGeneralInfo(UUID associationId, AssociationGeneralInfoRequestDTO associationGeneralInfoDTO) {
    Association association = this.getAuthenticatedAssociationById(associationId);
    association.setFounder(associationGeneralInfoDTO.founder());
    association.setFoundationDate(associationGeneralInfoDTO.foundationDate());
    associationRepository.save(association);
  }

  public AssociationCardResponseDTO getAssociationCard(UUID id, UUID authenticatedUserId) {
    Association association = associationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Association not found"));
    return associationCardResponseMapper.fromEntityToDTO(association, authenticatedUserId);
  }

  public List<AssociationCardResponseDTO> getAssociationCards(List<UUID> ids, UUID authenticatedUserId) {
    List<Association> associations = associationRepository.findAllById(ids);

    return associations.stream().map(association -> associationCardResponseMapper.fromEntityToDTO(association, authenticatedUserId)).toList();
  }

  public AssociationGeneralInfoRequestDTO.AssociationLoginResponseDTO getMyAssociation(UUID id) {
    Association association = associationRepository.findById(id).orElse(null);
    return associationLoginResponseMapper.fromEntityToDTO(association);
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

  @Transactional
  public void updateDescription(UUID associationId, String description) {
    Association association = this.getAuthenticatedAssociationById(associationId);
    association.setDescription(description);
    associationRepository.save(association);
  }

  @Transactional
  public void updateStatistics(UUID associationId, List<StatisticDTO> newStats) {
    Association association = this.getAuthenticatedAssociationById(associationId);
    List<Statistic> existingStats = association.getStatistics();
    existingStats.clear();
    for (StatisticDTO dto : newStats) {
      Statistic stat = new Statistic(dto.value(), dto.description(), association);
      existingStats.add(stat);
    }
    associationRepository.save(association);
  }

  public List<ImageResponseDTO> getExistingActivityPictures(UUID associationId, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    Page<Image> pagedImages = activityImageRepository.findDistinctImagesByAssociationId(associationId, pageable);
    List<UUID> imageIds = pagedImages.getContent().stream().map(Image::getId).toList();
    return imageMapper.toResponseDTOs(imageIds, ImageEnumType.ACTIVITY);
  }

  @Transactional
  public void uploadLogo(UUID associationId, MultipartFile logoFile) {
    uploadAssociationImage(associationId, logoFile, ImageEnumType.LOGO);
  }

  @Transactional
  public void uploadCover(UUID associationId, MultipartFile coverFile) {
    uploadAssociationImage(associationId, coverFile, ImageEnumType.PROFILE_ASSOCIATION);
  }

  private void uploadAssociationImage(UUID associationId, MultipartFile file, ImageEnumType type) {
    Association association = associationRepository.findById(associationId).orElseThrow(() -> new ResourceNotFoundException("Association not found"));

    List<AssociationImage> existingImages = association.getAssociationImages().stream().filter(ai -> ai.getImage().getType() == type).toList();

    existingImages.forEach(ai -> {
      association.getAssociationImages().remove(ai);
      imageRepository.delete(ai.getImage());
    });

    Image image = imageService.uploadImage(file, type, "Association");

    AssociationImage associationImage = new AssociationImage();
    associationImage.setAssociation(association);
    associationImage.setImage(image);

    association.getAssociationImages().add(associationImage);

    associationRepository.save(association);
  }
}
