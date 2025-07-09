package com.back_alasso.AssociationFollower;

import com.back_alasso.Association.Association;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.core.BaseEntity;
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
