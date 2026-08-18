package com.Ali_Choopani.Task_Management_System.dto.user;

import com.Ali_Choopani.Task_Management_System.entities.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserSummary(Long id, @JsonProperty("phone number")String phoneNumber, String email, UserRole role) {
}
