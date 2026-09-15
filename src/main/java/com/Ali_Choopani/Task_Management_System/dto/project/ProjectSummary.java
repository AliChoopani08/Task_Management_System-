package com.Ali_Choopani.Task_Management_System.dto.project;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProjectSummary(Long id,
                             String title,
                             MemberSummary manager) {
}
