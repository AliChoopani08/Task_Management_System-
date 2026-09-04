package com.Ali_Choopani.Task_Management_System.services.task;

import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskDetails;
import com.Ali_Choopani.Task_Management_System.dto.task.UserTasksSummary;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskDetails createANewTaskOfProject(Long projectId, Long managerId, CreateTaskRequest request);
    TaskDetails assignToProjectMember(Long taskId, Long projectId, Long memberId, Long managerId);
    UserTasksSummary getUserTasksSummary(Long userId, Pageable pageable);
}
