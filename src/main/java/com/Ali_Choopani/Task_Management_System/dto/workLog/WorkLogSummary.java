package com.Ali_Choopani.Task_Management_System.dto.workLog;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record WorkLogSummary(Long id,String description,
                             @JsonProperty("created at")LocalDateTime createdAt) {
}
