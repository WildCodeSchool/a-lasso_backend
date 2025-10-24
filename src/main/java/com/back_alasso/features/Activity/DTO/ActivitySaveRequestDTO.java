package com.back_alasso.features.Activity.DTO;

import com.back_alasso.features.Address.DTO.AddressRequestDTO;
import com.back_alasso.features.Geolocation.DTO.GeolocationRequestDTO;
import com.back_alasso.features.Image.DTO.ImageActivityCreationRequestDTO;
import com.back_alasso.features.Theme.ThemeNameEnumType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySaveRequestDTO {

  private UUID id;

  @NotNull(groups = OnPublish.class)
  private ActivityStatusEnumType status;

  @NotEmpty(groups = OnPublish.class)
  private List<@Valid ImageActivityCreationRequestDTO> images;

  @NotBlank(groups = OnPublish.class)
  private String title;

  @NotNull(groups = OnPublish.class, message = "Le nombre de volontaires est obligatoire")
  @Min(groups = OnPublish.class, value = 1, message = "Le nombre de volontaires doit être supérieur à 0")
  private Long requestedVolunteers;

  @NotNull(groups = OnPublish.class)
  private LocalDateTime dateTime;

  @Valid
  @NotNull(groups = OnPublish.class)
  private AddressRequestDTO address;

  @Valid
  @NotNull(groups = OnPublish.class)
  private GeolocationRequestDTO location;

  @NotEmpty(groups = OnPublish.class)
  private List<ThemeNameEnumType> themes;

  @NotBlank(groups = OnPublish.class)
  private String description;
}
