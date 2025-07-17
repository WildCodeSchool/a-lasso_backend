package com.back_alasso.features.UserMessage;

import java.util.UUID;

public record UserMessageNotificationDTO(UUID activityId, String activityTitle, int countMessagesNotRead) {}
