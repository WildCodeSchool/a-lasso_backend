package com.back_alasso.AssociationImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssociationImageRepository extends JpaRepository<AssociationImage, UUID> {
}
