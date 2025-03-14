package com.back_alasso.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageCreationDTO(UUID activityId, String content, LocalDateTime date) {}
