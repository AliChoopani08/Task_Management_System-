package com.Ali_Choopani.Task_Management_System.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record ProjectDetails(Long id,
                             String title,
                             String description,
                             @JsonProperty("start date")LocalDate startDate,
                             @JsonProperty("due date")LocalDate dueDate,
                             MemberSummary manager){
}
