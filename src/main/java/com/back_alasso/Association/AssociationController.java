package com.back_alasso.Association;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@RestController
@RequestMapping("/association")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class AssociationController {

    private final AssociationService associationService;

    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<AssociationCardDTO> getAssociationCard(@PathVariable UUID id) {
        AssociationCardDTO associationCard = associationService.getAssociation(id);
        return ResponseEntity.status(HttpStatus.OK).body(associationCard);
    }
}
