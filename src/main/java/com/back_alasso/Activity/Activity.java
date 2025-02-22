package com.back_alasso.Activity;

import com.back_alasso.ActivityImage.ActivityImage;
import com.back_alasso.ActivityTheme.ActivityTheme;
import com.back_alasso.ActivityVoluntary.ActivityVoluntary;
import com.back_alasso.Address.Address;
import com.back_alasso.Association.Association;
import com.back_alasso.Geolocalisation.Geolocalisation;
import com.back_alasso.Message.Message;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Activity extends BaseEntity {

    public static final int TITLE_MAX_LENGTH = 50;

    @Column(nullable = false, length = TITLE_MAX_LENGTH)
    private String title;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long volontaries_request;

    @ManyToOne
    @JoinColumn(name = "association_id")
    private Association association;

    @ManyToOne
    @JoinColumn(name = "adress_id")
    private Address address;

    @OneToMany(mappedBy = "activity")
    private List<ActivityImage> activityImages;

    @OneToMany(mappedBy = "activity")
    private List<ActivityTheme> activityThemes;

    @OneToMany(mappedBy = "activity")
    private List<ActivityVoluntary> activityVoluntaries;

    @OneToMany(mappedBy = "activity")
    private List<Message> messages;

    @ManyToOne
    @JoinColumn(name = "geolocalisation_id")
    private Geolocalisation geolocalisation;

    public Activity(String title, LocalDateTime date, String description, Long volontaries_request, Association association, Address address, List<ActivityImage> activityImages, List<ActivityTheme> activityThemes) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.volontaries_request = volontaries_request;
        this.association = association;
        this.address = address;
        this.activityImages = activityImages;
        this.activityThemes = activityThemes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVolontaries_request() {
        return volontaries_request;
    }

    public void setVolontaries_request(Long volontaries_request) {
        this.volontaries_request = volontaries_request;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ActivityImage> getActivityImages() {
        return activityImages;
    }

    public void setActivityImages(List<ActivityImage> activityImages) {
        this.activityImages = activityImages;
    }

    public List<ActivityTheme> getActivityThemes() {
        return activityThemes;
    }

    public void setActivityThemes(List<ActivityTheme> activityThemes) {
        this.activityThemes = activityThemes;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<ActivityVoluntary> getActivityVoluntaries() {
        return activityVoluntaries;
    }

    public void setActivityVoluntaries(List<ActivityVoluntary> activityVoluntaries) {
        this.activityVoluntaries = activityVoluntaries;
    }

    public List<Message> getMessage() {
        return messages;
    }

    public void setMessage(List<Message> messages) {
        this.messages = messages;
    }

    public Geolocalisation getGeolocalisation() {
        return geolocalisation;
    }

    public void setGeolocalisation(Geolocalisation geolocalisation) {
        this.geolocalisation = geolocalisation;
    }
}
