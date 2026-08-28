package com.Ali_Choopani.Task_Management_System.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record ProjectSummary(Long id,
                             String title,
                             @JsonProperty("manager") MemberSummary manager) {
}
