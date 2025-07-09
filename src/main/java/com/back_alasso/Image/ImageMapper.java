package com.back_alasso.Image;

import com.back_alasso.Image.DTO.ImageResponseDTO;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

  private ImageRepository imageRepository;

  @Value("${custom.api-url}")
  private String apiUrl;

  public ImageMapper(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public List<ImageResponseDTO> toResponseDTOs(List<UUID> imagesId, ImageEnumType type) {
    List<Image> images;

    if (type != null) {
      images = imageRepository.findAllByIdInAndType(imagesId, type);
    } else {
      images = imageRepository.findAllById(imagesId);
    }

    return images
      .stream()
      .map(image -> {
        if (image.getData() == null) {
          return new ImageResponseDTO(image.getId(), apiUrl + image.getUrl());
        }

        byte[] data = image.getData();
        String base64 = data != null ? "data:image/png;base64," + Base64.getEncoder().encodeToString(data) : null;

        return new ImageResponseDTO(image.getId(), base64);
      })
      .toList();
  }
}
