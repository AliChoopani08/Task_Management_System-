package com.Ali_Choopani.Task_Management_System.services.comment;

import com.Ali_Choopani.Task_Management_System.dto.comment.CommentSummary;
import com.Ali_Choopani.Task_Management_System.dto.comment.CreateCommentRequest;

public interface CommentService {

    CommentSummary createComment(Long authorId, Long taskId, CreateCommentRequest request);
}
