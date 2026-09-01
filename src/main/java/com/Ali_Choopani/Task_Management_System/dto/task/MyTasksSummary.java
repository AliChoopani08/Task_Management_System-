package com.Ali_Choopani.Task_Management_System.dto.task;

import com.Ali_Choopani.Task_Management_System.entities.TaskStatus;

public record MyTasksSummary(Long id,
                             String title,
                             TaskStatus status) {
}
