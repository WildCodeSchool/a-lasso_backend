package com.back_alasso.features.Image;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, UUID> {
  Optional<Image> findFirstByUrl(String url);

  List<Image> findAllByIdInAndType(List<UUID> ids, ImageEnumType type);
}
