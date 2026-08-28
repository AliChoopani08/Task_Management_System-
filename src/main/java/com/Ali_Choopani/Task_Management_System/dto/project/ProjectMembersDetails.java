package com.Ali_Choopani.Task_Management_System.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record ProjectMembersDetails(@JsonProperty("project id")Long projectId,
                                    @JsonProperty("project title") String projectTitle,
                                    Set<MemberSummary> members) {
}
