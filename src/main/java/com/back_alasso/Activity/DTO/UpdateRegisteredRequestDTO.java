package com.back_alasso.Activity.DTO;

import jakarta.validation.constraints.NotNull;

public record UpdateRegisteredRequestDTO(@NotNull(message = "Le nouvel état doit être renseigné") Boolean isRegistered) {}
