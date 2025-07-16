package com.back_alasso.features.ActivityImage;

import com.back_alasso.features.Image.Image;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, UUID> {
  Page<ActivityImage> findByActivity_Association_Id(UUID associationId, Pageable pageable);

  @Query(
    """
    SELECT DISTINCT ai.image
    FROM ActivityImage ai
    WHERE ai.activity.association.id = :associationId
    """
  )
  Page<Image> findDistinctImagesByAssociationId(@Param("associationId") UUID associationId, Pageable pageable);
}
