package com.Ali_Choopani.Task_Management_System.testFactories;

import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.AssigneeSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskDetails;
import com.Ali_Choopani.Task_Management_System.entities.Project;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Management_System.entities.Task;
import com.Ali_Choopani.Task_Management_System.entities.TaskStatus;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;

public class TaskTestFactory {

    public static Task createTask(Long id, String title, String description, LocalDate duaDate, TaskStatus status, Project project, ProjectMember projectMember) {
        Task task = Task.builder()
                .id(id)
                .title(title)
                .description(description)
                .dueDate(duaDate)
                .status(status)
                .build();

        task.addTaskProject(project);
        ofNullable(projectMember)
                .ifPresent(task::assignTaskToMember);

        return task;
    }

    public static TaskDetails createTaskSummary(Task task, ProjectSummary projectSummary) {
        TaskDetails.TaskDetailsBuilder summaryBuilder = TaskDetails.builder();
        ofNullable(task.getAssignee())
                .ifPresent(a -> summaryBuilder.assignee(
                        new AssigneeSummary(a.getMember().getId(), a.getMember().getProfile().getFullName())));

        return summaryBuilder
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .project(projectSummary)
                .build();
    }
}
