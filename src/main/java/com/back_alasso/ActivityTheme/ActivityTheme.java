package com.back_alasso.ActivityTheme;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Theme.Theme;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ActivityTheme extends BaseEntity {

    @Column(nullable = false)
    private boolean is_saved = false;

    @Column(nullable = false)
    private boolean is_registered = false;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    public boolean isIs_saved() {
        return is_saved;
    }

    public void setIs_saved(boolean is_saved) {
        this.is_saved = is_saved;
    }

    public boolean isIs_registered() {
        return is_registered;
    }

    public void setIs_registered(boolean is_registered) {
        this.is_registered = is_registered;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
