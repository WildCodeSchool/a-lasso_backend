package com.back_alasso.features.Association;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<Association, UUID> {}
