package com.back_alasso.ActivityTheme;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Theme.Theme;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTheme extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "activity_id")
  private Activity activity;

  @ManyToOne
  @JoinColumn(name = "theme_id")
  private Theme theme;
}
