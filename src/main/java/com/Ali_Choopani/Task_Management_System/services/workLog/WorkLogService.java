package com.Ali_Choopani.Task_Management_System.services.workLog;

import com.Ali_Choopani.Task_Management_System.dto.workLog.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogDetails;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogSummary;

import java.util.Set;

public interface WorkLogService {

    WorkLogDetails createWorkLog(Long authorId, Long taskId, CreateWorkLogRequest request);
    Set<WorkLogSummary> getWorkLogsSummaryOfFoundTaskAndUser(Long taskId, Long assigneeId);
}
