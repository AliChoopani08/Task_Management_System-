package com.Ali_Choopani.Task_Managment_System.dto.user.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;
import java.time.Period;

@Builder
public record ProfileSummary(Long id,
                             @JsonProperty("first name")String firstName,
                             @JsonProperty("surname")String surname,
                             @JsonProperty("age") String age,
                             String biography,
                             @JsonProperty("user id")Long userId
) {
}
