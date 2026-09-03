package com.Ali_Choopani.Task_Management_System.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WorkLogSummary(@JsonProperty("task id") Long taskId,
                             @JsonProperty("task title") String taskTitle,
                             String description,
                             @JsonProperty("created at")LocalDateTime createdAt,
                             @JsonProperty("author id") Long authorId,
                             @JsonProperty("author name") String authorName
                             ) {
}
