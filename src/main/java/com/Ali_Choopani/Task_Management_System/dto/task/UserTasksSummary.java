package com.Ali_Choopani.Task_Management_System.dto.task;

import org.springframework.data.domain.Page;

public record UserTasksSummary(Long userId,
                               String userName,
                               Page<MyTasksSummary> tasks) {
}
