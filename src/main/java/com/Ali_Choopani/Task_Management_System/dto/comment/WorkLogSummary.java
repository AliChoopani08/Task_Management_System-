package com.Ali_Choopani.Task_Management_System.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentSummary(Long id, String description,
                             @JsonProperty("created at")LocalDateTime createdAt,
                             @JsonProperty("author id") Long authorId,
                             @JsonProperty("task id") Long taskId) {
}
