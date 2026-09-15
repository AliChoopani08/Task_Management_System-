package com.Ali_Choopani.Task_Management_System.dto.task;

import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.entities.TaskStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskDetails(Long id, String title, String description,
                          LocalDate createdAt,
                           LocalDate dueDate,
                          AssigneeSummary assignee,
                          ProjectSummary project,
                          TaskStatus status) {
}
