package com.back_alasso.AssociationImage;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationImageRepository extends JpaRepository<AssociationImage, UUID> {
  List<AssociationImage> findByAssociation_id(UUID associationId);
}
