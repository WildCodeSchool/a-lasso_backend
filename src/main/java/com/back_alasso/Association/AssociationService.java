package com.back_alasso.Association;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AssociationService {

    private final AssociationRepository associationRepository;

    public AssociationService(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    public AssociationCardDTO getAssociation(UUID id) {
        Association association = associationRepository.findById(id).orElse(null);
        return AssociationCardDTO.fromEntityToDTO(association);
    }

}
