package com.Ali_Choopani.Task_Management_System.services.comment;

import com.Ali_Choopani.Task_Management_System.dto.comment.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.comment.WorkLogSummary;
import com.Ali_Choopani.Task_Management_System.entities.WorkLog;
import com.Ali_Choopani.Task_Management_System.entities.Task;
import com.Ali_Choopani.Task_Management_System.exceptions.task.NotFoundTaskAndAssigneeException;
import com.Ali_Choopani.Task_Management_System.exceptions.task.NotMatchTaskWithAssigneeException;
import com.Ali_Choopani.Task_Management_System.mappers.WorkLogMapper;
import com.Ali_Choopani.Task_Management_System.repositories.WorkLogRepository;
import com.Ali_Choopani.Task_Management_System.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository repository;
    private final TaskRepository taskRepository;
    private final WorkLogMapper mapper;

    @Override
    @Transactional
    public WorkLogSummary createWorkLog(Long authorId, Long taskId, CreateWorkLogRequest request) {
        final Task task = taskRepository.findByIdAndAssigneeId(taskId, authorId)
                .orElseThrow(() -> new NotFoundTaskAndAssigneeException(taskId, authorId));

        if (!task.getProject().getId().equals(task.getAssignee().getProject().getId())) {
            throw new NotMatchTaskWithAssigneeException(taskId, authorId);
        }
        final WorkLog log = mapper.toEntity(request);
        log.addReportTask(task.getAssignee(), task);
        final WorkLog savedLog = repository.save(log);

        return mapper.toSummary(savedLog);
    }
}
