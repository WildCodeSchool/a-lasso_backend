package com.back_alasso.AssociationFollower;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationFollowerRepository extends JpaRepository<AssociationFollower, UUID> {
  Optional<AssociationFollower> findByVoluntary_idAndAssociation_id(UUID authenticatedUser, UUID associationId);
}
