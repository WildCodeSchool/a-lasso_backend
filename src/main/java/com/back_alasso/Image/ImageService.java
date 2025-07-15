package com.back_alasso.Image;

import com.back_alasso.Exception.ResourceNotFoundException;
import com.back_alasso.Image.DTO.ImageActivityCreationRequestDTO;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

  private final ImageRepository imageRepository;

  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
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
}
