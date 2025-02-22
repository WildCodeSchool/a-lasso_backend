package com.back_alasso.AssociationFollower;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssociationFollowerRepository extends JpaRepository<AssociationFollower, UUID> {
}
