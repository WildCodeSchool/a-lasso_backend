package com.back_alasso.ActivityImage;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, UUID> {
  Page<ActivityImage> findByActivity_Association_Id(UUID associationId, Pageable pageable);
}
