package com.back_alasso.UserMessage;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMessageRepository extends JpaRepository<UserMessage, UUID> {
  Optional<UserMessage> findByUserIdAndMessageId(UUID userId, UUID messageId);
}
