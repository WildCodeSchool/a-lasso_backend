package com.back_alasso.ActivityImage;

import com.back_alasso.Activity.Activity;
import com.back_alasso.Image.Image;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ActivityImage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public ActivityImage(Image image, Activity activity) {
        this.image = image;
        this.activity = activity;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
