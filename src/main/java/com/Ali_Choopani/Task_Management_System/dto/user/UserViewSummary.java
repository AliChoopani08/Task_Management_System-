package com.Ali_Choopani.Task_Management_System.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserViewSummary(Long id,
                              @JsonProperty("full name")String fullName) {
}
