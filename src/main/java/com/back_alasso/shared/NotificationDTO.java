package com.back_alasso.shared;

import com.back_alasso.features.UserMessage.UserMessageNotificationDTO;
import java.util.List;

public record NotificationDTO(List<UserMessageNotificationDTO> messages, Integer reports) {}
