package com.Ali_Choopani.Task_Management_System.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record ProjectSummary(Long id,
                             String title, String description,
                             @JsonProperty("start date") LocalDate startDate,
                             @JsonProperty("due date") LocalDate dueDate,
                             @JsonProperty("manager") MemberSummary manager) {
}
