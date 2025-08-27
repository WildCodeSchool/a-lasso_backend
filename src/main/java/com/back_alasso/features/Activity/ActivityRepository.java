package com.back_alasso.features.Activity;

import com.back_alasso.features.Voluntary.Voluntary;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

  List<Activity> findAllByAssociation_id(UUID associationId);

  List<Activity> findByAssociationId(UUID associationId);

  @Query(
    """
        SELECT a
        FROM Activity a
        WHERE a.association.account_status <> 'BANNED'
            AND a.date >= :now
    """
  )
  List<Activity> findAllFromNotBannedAssociationsAndFuture(@Param("now") LocalDateTime now);

  @Query(
    """
        SELECT a
        FROM Activity a
        WHERE a.id = :activityId AND a.association.account_status <> 'BANNED'
    """
  )
  Optional<Activity> findByIdFromNotBannedAssociation(@Param("activityId") UUID activityId);
}
