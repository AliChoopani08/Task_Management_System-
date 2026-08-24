package com.Ali_Choopani.Task_Management_System.dto.project;

import com.Ali_Choopani.Task_Management_System.entities.ProjectRole;
import lombok.Builder;

@Builder
public record MyProjectsSummary(Long id, String title, ProjectRole role) {
}
