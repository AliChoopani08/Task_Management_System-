package com.Ali_Choopani.Task_Management_System.dto.workLog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WorkLogDetails(Long taskId,
                             String taskTitle,
                             String description,
                             LocalDateTime createdAt,
                             Long authorId,
                             String authorName
) {
}
