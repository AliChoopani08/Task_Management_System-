package com.Ali_Choopani.Task_Management_System.dto.project;

import com.Ali_Choopani.Task_Management_System.dto.PageResponse;

public record ProjectMembersDetails(Long projectId,
                                    String projectTitle,
                                    PageResponse<MemberSummary> members) {
}
