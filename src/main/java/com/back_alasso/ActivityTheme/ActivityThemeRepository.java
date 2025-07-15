package com.back_alasso.ActivityTheme;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityThemeRepository extends JpaRepository<ActivityTheme, UUID> {
  void deleteByActivityId(UUID activityId);
}
