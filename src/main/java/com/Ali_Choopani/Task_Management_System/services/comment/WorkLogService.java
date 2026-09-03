package com.Ali_Choopani.Task_Management_System.services.comment;

import com.Ali_Choopani.Task_Management_System.dto.comment.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.comment.WorkLogSummary;

public interface WorkLogService {

    WorkLogSummary createWorkLog(Long authorId, Long taskId, CreateWorkLogRequest request);
}
