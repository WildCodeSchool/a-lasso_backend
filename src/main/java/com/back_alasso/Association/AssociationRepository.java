package com.back_alasso.Association;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssociationRepository extends JpaRepository<Association, UUID> {
}
