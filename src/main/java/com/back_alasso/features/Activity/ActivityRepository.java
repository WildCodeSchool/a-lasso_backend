package com.back_alasso.features.Activity;

import com.back_alasso.features.Voluntary.Voluntary;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
  @Query(
    """
        SELECT av.voluntary
        FROM ActivityVoluntary av
        WHERE av.activity.id = :activityId
        AND av.registered = true
    """
  )
  List<Voluntary> findVolunteersByActivityId(@Param("activityId") UUID activityId);

  List<Activity> findByAssociationId(UUID associationId);
}
