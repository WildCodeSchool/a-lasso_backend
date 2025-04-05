package com.back_alasso.ActivityVoluntary;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityVoluntaryRepository extends JpaRepository<ActivityVoluntary, UUID> {
  Optional<ActivityVoluntary> findByVoluntary_idAndActivity_id(UUID authenticatedUser, UUID activityId);
}
