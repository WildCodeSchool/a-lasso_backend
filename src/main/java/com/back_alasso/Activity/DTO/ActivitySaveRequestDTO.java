package com.back_alasso.Activity.DTO;

import com.back_alasso.Address.AddressRequestDTO;
import com.back_alasso.Geolocation.GeolocationDTO;
import com.back_alasso.Image.DTO.ImageActivityCreationRequestDTO;
import com.back_alasso.Theme.ThemeNameEnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ActivitySaveRequestDTO {

    private UUID id;

    @NotNull(groups = OnPublish.class)
    private ActivityStatusEnumType status;

    @NotEmpty(groups = OnPublish.class)
    private List<ImageActivityCreationRequestDTO> images;

    @NotBlank(groups = OnPublish.class)
    private String title;

    @NotNull(groups = OnPublish.class)
    private Long requestedVolunteers;

    @NotNull(groups = OnPublish.class)
    private LocalDateTime dateTime;

    @NotNull(groups = OnPublish.class)
    private AddressRequestDTO address;

    @NotNull(groups = OnPublish.class)
    private GeolocationDTO location;

    @NotEmpty(groups = OnPublish.class)
    private List<ThemeNameEnumType> themes;

    @NotBlank(groups = OnPublish.class)
    private String description;

    // Getters and setters

    public ActivityStatusEnumType getStatus() {
        return status;
    }

    public void setStatus(ActivityStatusEnumType status) {
        this.status = status;
    }

    public List<ImageActivityCreationRequestDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageActivityCreationRequestDTO> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRequestedVolunteers() {
        return requestedVolunteers;
    }

    public void setRequestedVolunteers(Long requestedVolunteers) {
        this.requestedVolunteers = requestedVolunteers;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AddressRequestDTO getAddress() {
        return address;
    }

    public void setAddress(AddressRequestDTO address) {
        this.address = address;
    }

    public List<ThemeNameEnumType> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemeNameEnumType> themes) {
        this.themes = themes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GeolocationDTO getLocation() {
        return location;
    }

    public void setLocation(GeolocationDTO location) {
        this.location = location;
    }
}
