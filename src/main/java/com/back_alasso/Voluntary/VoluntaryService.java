package com.back_alasso.Voluntary;

import com.back_alasso.Exception.ResourceNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoluntaryService {

  private final VoluntaryRepository voluntaryRepository;

  public VoluntaryService(VoluntaryRepository voluntaryRepository) {
    this.voluntaryRepository = voluntaryRepository;
  }

  public Voluntary getVoluntaryById(UUID authenticatedUser) {
    return voluntaryRepository.findById(authenticatedUser).orElseThrow(() -> new ResourceNotFoundException("Voluntary not found"));
  }
}
