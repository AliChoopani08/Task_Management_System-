package com.Ali_Choopani.Task_Management_System.dto.project;

import com.Ali_Choopani.Task_Management_System.entities.ProjectRole;
import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberSummary(@JsonProperty("id") Long id,
                            @JsonProperty("name") String name,
                            @JsonProperty("role") ProjectRole role) {
}
