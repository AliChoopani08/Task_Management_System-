package com.Ali_Choopani.Task_Management_System.dto.user.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record DeviceSummary(Long id  ,@JsonProperty("device UUID")UUID deviceUuid) {
}
