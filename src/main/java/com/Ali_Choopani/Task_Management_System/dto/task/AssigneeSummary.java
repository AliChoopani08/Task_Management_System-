package com.Ali_Choopani.Task_Management_System.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AssigneeSummary(Long id, @JsonProperty("full name")String fullName) {
}
