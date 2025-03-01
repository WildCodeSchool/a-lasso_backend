package com.back_alasso.Voluntary;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoluntaryRepository extends JpaRepository<Voluntary, UUID> {}
