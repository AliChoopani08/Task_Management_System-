package com.Ali_Choopani.Task_Management_System.dto.project;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record ProjectDetails(Long id,
                             String title,
                             String description,
                             LocalDate startDate,
                             LocalDate dueDate,
                             MemberSummary manager){
}
