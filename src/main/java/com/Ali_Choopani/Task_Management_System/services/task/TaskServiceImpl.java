package com.Ali_Choopani.Task_Management_System.services.task;

import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskSummary;
import com.Ali_Choopani.Task_Management_System.entities.Project;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Management_System.entities.Task;
import com.Ali_Choopani.Task_Management_System.exceptions.DuplicateTaskInProject;
import com.Ali_Choopani.Task_Management_System.exceptions.project.NotFoundMemberInProjectException;
import com.Ali_Choopani.Task_Management_System.exceptions.project.NotFoundProjectAndMemberException;
import com.Ali_Choopani.Task_Management_System.exceptions.task.NotFoundTaskException;
import com.Ali_Choopani.Task_Management_System.mappers.TaskMapper;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.TaskStatus.TODO;
import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository repository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TaskMapper mapper;

    @Override
    public TaskSummary createANewTaskOfProject(Long projectId, Long managerId, CreateTaskRequest request) {
        final ProjectMember foundProjectManager = projectMemberRepository.findByProjectIdAndMemberIdAndRole(projectId, managerId, ROLE_MANAGER)
                .orElseThrow(() -> new NotFoundProjectAndMemberException(projectId, managerId, ROLE_MANAGER));
        final Project project = foundProjectManager.getProject();

        repository.findByProjectIdAndTitleIgnoreCase(project.getId(), request.getTitle())
                .ifPresent(__ -> {
                    throw new DuplicateTaskInProject(request.getTitle(), project.getId());
                });
        final Task task = mapper.toEntity(request);
        task.setStatus(TODO);
        task.setCreateAt(now());
        task.addTaskProject(project);

        final Task savedNewTask = repository.save(task);

        return mapper.toSummary(savedNewTask, foundProjectManager);
    }

    @Override
    public TaskSummary assignToProjectMember(Long taskId, Long projectId, Long memberId, Long managerId) {
        final ProjectMember projectManager = projectMemberRepository.findByProjectIdAndMemberIdAndRole(projectId, managerId, ROLE_MANAGER)
                .orElseThrow(() -> new NotFoundProjectAndMemberException(projectId, managerId, ROLE_MANAGER));
        final ProjectMember projectMember = projectMemberRepository.findByProjectIdAndMemberId(projectId, memberId)
                .orElseThrow(() -> new NotFoundMemberInProjectException(memberId, projectId));
        final Task task = repository.findByProjectIdAndId(projectId,taskId)
                .orElseThrow(() -> new NotFoundTaskException(taskId));

        task.assignTaskToMember(projectMember);

        final Task updatedTask = repository.save(task);

        return mapper.toSummary(updatedTask, projectManager);
    }
}
