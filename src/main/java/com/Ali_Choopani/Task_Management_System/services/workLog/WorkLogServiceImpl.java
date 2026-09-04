package com.Ali_Choopani.Task_Management_System.services.workLog;

import com.Ali_Choopani.Task_Management_System.dto.workLog.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogDetails;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogSummary;
import com.Ali_Choopani.Task_Management_System.entities.WorkLog;
import com.Ali_Choopani.Task_Management_System.entities.Task;
import com.Ali_Choopani.Task_Management_System.exceptions.task.NotFoundTaskAndAssigneeException;
import com.Ali_Choopani.Task_Management_System.exceptions.task.NotMatchTaskWithAssigneeException;
import com.Ali_Choopani.Task_Management_System.mappers.WorkLogMapper;
import com.Ali_Choopani.Task_Management_System.repositories.WorkLogRepository;
import com.Ali_Choopani.Task_Management_System.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@Service
@RequiredArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository repository;
    private final TaskRepository taskRepository;
    private final WorkLogMapper mapper;

    @Override
    @Transactional
    public WorkLogDetails createWorkLog(Long authorId, Long taskId, CreateWorkLogRequest request) {
        final Task task = taskRepository.findByIdAndUserId(taskId, authorId)
                .orElseThrow(() -> new NotFoundTaskAndAssigneeException(taskId, authorId));

        if (!task.getProject().getId().equals(task.getAssignee().getProject().getId())) {
            throw new NotMatchTaskWithAssigneeException(taskId, authorId);
        }
        final WorkLog log = mapper.toEntity(request);
        log.addReportTask(task.getAssignee(), task);
        final WorkLog savedLog = repository.save(log);

        return mapper.toDetails(savedLog);
    }

    @Override
    public Set<WorkLogSummary> getWorkLogsSummaryOfFoundTaskAndUser(Long taskId, Long userId) {
        final Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new NotFoundTaskAndAssigneeException(taskId, userId));

        final Page<WorkLog> workLogs = repository.findByTaskId(task.getId(),
                of(0, 20, by(DESC, "createdAt")));

        return workLogs.getContent()
                .stream().map(mapper::toSummary)
                .collect(toSet());
    }
}
