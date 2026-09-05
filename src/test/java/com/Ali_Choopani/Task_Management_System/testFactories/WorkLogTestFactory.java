package com.Ali_Choopani.Task_Management_System.testFactories;

import com.Ali_Choopani.Task_Management_System.entities.WorkLog;
import com.Ali_Choopani.Task_Management_System.entities.ProjectMember;
import com.Ali_Choopani.Task_Management_System.entities.Task;

public class WorkLogTestFactory {

    public static WorkLog createWorkLog(Long id, String description, ProjectMember author, Task task) {
        final WorkLog comment = WorkLog.builder()
                .id(id)
                .description(description)
                .build();
        comment.addReportTask(author, task);

        return comment;
    }
}
