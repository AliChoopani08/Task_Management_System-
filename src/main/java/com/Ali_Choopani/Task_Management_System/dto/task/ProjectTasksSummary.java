package com.Ali_Choopani.Task_Management_System.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProjectTasksSummary(@JsonProperty("task id")Long taskId,
                                  @JsonProperty("task title") String taskTitle,
                                  @JsonProperty("assignee id") Long assigneeId,
                                  @JsonProperty("assignee full name") String assigneeFullName,
                                  @JsonProperty("project id") Long projectId,
                                  @JsonProperty("project title") String projectTitle) {
}
