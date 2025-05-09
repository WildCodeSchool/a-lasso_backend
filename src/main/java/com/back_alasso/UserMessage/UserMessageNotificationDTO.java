package com.back_alasso.UserMessage;

import java.util.UUID;

public record UserMessageNotificationDTO(UUID activityId, String activityTitle, int countMessagesNotRead) {}
