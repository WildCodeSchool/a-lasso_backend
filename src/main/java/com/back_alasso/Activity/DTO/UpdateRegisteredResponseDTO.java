package com.back_alasso.Activity.DTO;

import com.back_alasso.ActivityVoluntary.ActivityVoluntaryDTO;

public record UpdateRegisteredResponseDTO(Boolean isRegistered, ActivityVoluntaryDTO activityVoluntaryDTO) {}
