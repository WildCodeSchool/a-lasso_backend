package com.back_alasso.ActivityVoluntary;

import com.back_alasso.Activity.Activity;
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
public class ActivityVoluntary extends BaseEntity {

  @Column(nullable = false)
  private boolean saved;

  @Column(nullable = false)
  private boolean registered;

  @ManyToOne
  @JoinColumn(name = "voluntary_id")
  private Voluntary voluntary;

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;
}
