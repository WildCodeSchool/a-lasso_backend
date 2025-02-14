package com.back_alasso.ActivityTheme;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Theme.Theme;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ActivityTheme {

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
}
