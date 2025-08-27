package com.back_alasso.features.Image;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Image.DTO.ImageActivityCreationRequestDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

  private final ImageRepository imageRepository;

  public List<Image> processImages(List<ImageActivityCreationRequestDTO> imageDTOs) {
    if (imageDTOs == null || imageDTOs.isEmpty()) return Collections.emptyList();

    List<Image> result = new ArrayList<>();
    List<Image> newImages = new ArrayList<>();

    for (ImageActivityCreationRequestDTO dto : imageDTOs) {
      if (dto.id() != null) {
        Image existing = imageRepository.findById(dto.id()).orElseThrow(() -> new ResourceNotFoundException("Image not found with ID: " + dto.id()));
        result.add(existing);
      } else if (StringUtils.isNotBlank(dto.base64())) {
        String base64 = dto.base64();
        if (base64.startsWith("data:")) {
          base64 = base64.substring(base64.indexOf(",") + 1);
        }
        byte[] imageData = Base64.getDecoder().decode(base64);

        Image image = new Image();
        image.setData(imageData);
        image.setType(ImageEnumType.ACTIVITY);
        image.setUrl("");
        newImages.add(image);
      }
    }

    List<Image> savedNewImages = imageRepository.saveAll(newImages);
    result.addAll(savedNewImages);
    return result;
  }

  public Image uploadImage(MultipartFile file, ImageEnumType type, String folder) {
    try {
      String originalFilename = file.getOriginalFilename();
      String extension = extractExtension(originalFilename);

      validateExtension(extension);

      String cleanedFilename = cleanFilename(originalFilename != null ? originalFilename : type.name().toLowerCase());
      String uniqueFilename = UUID.randomUUID() + "_" + cleanedFilename;
      String url = "/images/" + folder + "/" + uniqueFilename;

      Image image = new Image();
      image.setData(file.getBytes());
      image.setType(type);
      image.setFilename(uniqueFilename);
      image.setFormat(extension);
      image.setUrl(url);

      return imageRepository.save(image);
    } catch (IOException e) {
      throw new RuntimeException("Erreur lors de l'upload de l'image", e);
    }
  }

  private String extractExtension(String filename) {
    if (filename != null && filename.contains(".")) {
      return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
    return "";
  }

  private void validateExtension(String extension) {
    List<String> allowedExtensions = List.of("png", "jpg", "jpeg", "webp");
    if (!allowedExtensions.contains(extension)) {
      throw new RuntimeException("Format de fichier non autorisé : " + extension);
    }
  }

  private String cleanFilename(String filename) {
    return filename.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9._-]", "");
  }
}
