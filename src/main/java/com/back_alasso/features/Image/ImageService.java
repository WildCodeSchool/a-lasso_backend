package com.back_alasso.features.Image;

import com.back_alasso.exception.ResourceNotFoundException;
import com.back_alasso.features.Image.DTO.ImageActivityCreationRequestDTO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

  private final ImageRepository imageRepository;
  private final double ZERO_POINT_SIX = 0.6;
  private final double ONE = 1.0;

  private byte[] compressImage(byte[] originalBytes, double targetQuality, double scale) throws IOException {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(originalBytes);
    BufferedImage originalImage = ImageIO.read(inputStream);

    if (originalImage == null) {
      throw new IOException("Impossible de lire l'image");
    }

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    Thumbnails.of(originalImage)
      .scale(scale) // ex: 1.0 garde la taille, <1.0 réduit
      .outputQuality(targetQuality) // qualité JPEG/WebP (0.0 à 1.0)
      .outputFormat("jpeg")
      .toOutputStream(outputStream);

    return outputStream.toByteArray();
  }

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

        try {
          byte[] compressed = compressImage(imageData, ZERO_POINT_SIX, ONE);

          Image image = new Image();
          image.setData(compressed);
          image.setType(ImageEnumType.ACTIVITY);
          image.setUrl("");
          newImages.add(image);
        } catch (IOException e) {
          throw new RuntimeException("Erreur lors de la compression de l'image", e);
        }
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

      byte[] originalData = file.getBytes();

      byte[] compressedData = compressImage(originalData, ZERO_POINT_SIX, ONE);

      String cleanedFilename = cleanFilename(originalFilename != null ? originalFilename : type.name().toLowerCase());
      String uniqueFilename = UUID.randomUUID() + "_" + cleanedFilename;
      String url = "/images/" + folder + "/" + uniqueFilename;

      Image image = new Image();
      image.setData(compressedData);
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
