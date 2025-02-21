package com.back_alasso.Voluntary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoluntaryRepository extends JpaRepository<Voluntary, UUID> {
}
