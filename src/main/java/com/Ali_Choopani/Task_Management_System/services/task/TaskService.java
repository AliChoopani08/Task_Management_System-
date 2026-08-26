package com.Ali_Choopani.Task_Management_System.services.task;

import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskSummary;

public interface TaskService {
    TaskSummary createANewTaskOfProject(Long projectId, Long managerId, CreateTaskRequest request);
    TaskSummary assignToProjectMember(Long taskId, Long projectId, Long memberId, Long managerId);
}
