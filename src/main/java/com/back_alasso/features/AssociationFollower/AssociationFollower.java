package com.back_alasso.features.AssociationFollower;

import com.back_alasso.core.BaseEntity;
import com.back_alasso.features.Association.Association;
import com.back_alasso.features.Voluntary.Voluntary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociationFollower extends BaseEntity {

  @Column(nullable = false)
  private boolean notification_active = false;

  @Column(nullable = false)
  private boolean follow = false;

  @ManyToOne
  @JoinColumn(name = "voluntary_id")
  private Voluntary voluntary;

  @ManyToOne
  @JoinColumn(name = "association_id")
  private Association association;
}
