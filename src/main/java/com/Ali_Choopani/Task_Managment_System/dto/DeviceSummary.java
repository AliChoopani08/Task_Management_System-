package com.Ali_Choopani.Task_Managment_System.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record DeviceSummary(Long id  ,@JsonProperty("device UUID")UUID deviceUuid) {
}
