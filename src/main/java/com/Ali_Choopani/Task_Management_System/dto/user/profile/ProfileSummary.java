package com.Ali_Choopani.Task_Management_System.dto.user.profile;

import lombok.Builder;

@Builder
public record ProfileSummary(Long id,
                             String firstName,
                             String surname,
                             String age,
                             String biography,
                             Long userId
) {
}
