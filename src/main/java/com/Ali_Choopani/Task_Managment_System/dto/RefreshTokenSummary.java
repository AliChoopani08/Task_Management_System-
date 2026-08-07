package com.Ali_Choopani.Task_Managment_System.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RefreshTokenSummary(UUID token) {
}
