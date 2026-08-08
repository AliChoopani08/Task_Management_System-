package com.Ali_Choopani.Task_Managment_System.dto.user.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProfileSummary(Long id,
                             @JsonProperty("first name")String firstName,
                             @JsonProperty("surname")String surname,
                             @JsonProperty("birth date")LocalDate birthDate,
                             String biography,
                             @JsonProperty("user id")Long userId
) {
}
