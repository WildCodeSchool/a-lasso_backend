package com.back_alasso.ActivityVoluntary;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Voluntary.Voluntary;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ActivityVoluntary extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "voluntary_id")
    private Voluntary voluntary;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public Voluntary getVoluntary() {
        return voluntary;
    }

    public void setVoluntary(Voluntary voluntary) {
        this.voluntary = voluntary;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
