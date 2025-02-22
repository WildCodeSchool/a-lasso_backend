package com.back_alasso.Theme;

import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Theme extends BaseEntity {

    public static final int NAME_MAX_LENGTH = 12;
    public static final int ICON_URL_MAX_LENGTH = 255;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = NAME_MAX_LENGTH)
    private ThemeNameEnumType name;

    @Column(nullable = false, length = ICON_URL_MAX_LENGTH)
    private String icon_url;

    public Theme(ThemeNameEnumType name, String icon_url) {
        this.name = name;
        this.icon_url = icon_url;
    }

    public ThemeNameEnumType getName() {
        return name;
    }

    public void setName(ThemeNameEnumType name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
