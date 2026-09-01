package com.Ali_Choopani.Task_Management_System.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

public record UserTasksSummary(@JsonProperty("user id")Long userId,
                               @JsonProperty("user name") String userName,
                               Page<MyTasksSummary> tasks) {
}
