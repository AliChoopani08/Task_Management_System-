package com.Ali_Choopani.Task_Management_System.dto.project;

import lombok.Builder;

import java.util.Set;

@Builder
public record ProjectMemberSummary(ProjectSummary project,
                                   Set<MemberSummary> members) {
}
