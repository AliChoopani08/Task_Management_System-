package com.Ali_Choopani.Task_Management_System.dto.task;

import com.Ali_Choopani.Task_Management_System.TaskManagementSystemApplication;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.entities.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskSummary(Long id, String title, String description,
                          @JsonProperty("created at")LocalDate createdAt,
                          @JsonProperty("due date") LocalDate dueDate,
                          AssigneeSummary assignee,
                          ProjectSummary project,
                          TaskStatus status) {
}
