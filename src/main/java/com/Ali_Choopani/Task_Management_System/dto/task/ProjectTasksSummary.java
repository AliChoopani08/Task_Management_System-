package com.Ali_Choopani.Task_Management_System.dto.task;


public record ProjectTasksSummary(Long taskId,
                                  String taskTitle,
                                  Long assigneeId,
                                  String assigneeFullName,
                                  Long projectId,
                                  String projectTitle) {
}
