package com.back_alasso.ActivityImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, UUID> {
}
