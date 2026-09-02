package com.Ali_Choopani.Task_Management_System.services.comment;

import com.Ali_Choopani.Task_Management_System.dto.comment.CommentSummary;
import com.Ali_Choopani.Task_Management_System.dto.comment.CreateCommentRequest;
import com.Ali_Choopani.Task_Management_System.entities.Comment;
import com.Ali_Choopani.Task_Management_System.entities.Task;
import com.Ali_Choopani.Task_Management_System.exceptions.task.NotFoundTaskAndAssignee;
import com.Ali_Choopani.Task_Management_System.exceptions.task.NotMatchTaskWithAssignee;
import com.Ali_Choopani.Task_Management_System.mappers.CommentMapper;
import com.Ali_Choopani.Task_Management_System.repositories.CommentRepository;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository repository;
    private final TaskRepository taskRepository;
    private final CommentMapper mapper;

    @Override
    @Transactional
    public CommentSummary createComment(Long authorId, Long taskId, CreateCommentRequest request) {
        final Task task = taskRepository.findByIdAndAssigneeId(taskId, authorId)
                .orElseThrow(() -> new NotFoundTaskAndAssignee(taskId, authorId));

        if (!task.getProject().getId().equals(task.getAssignee().getProject().getId())) {
            throw new NotMatchTaskWithAssignee(taskId, authorId);
        }
        final Comment comment = mapper.toEntity(request);
        comment.addCommentTask(task.getAssignee(), task);
        final Comment savedComment = repository.save(comment);

        return mapper.toSummary(savedComment);
    }
}
