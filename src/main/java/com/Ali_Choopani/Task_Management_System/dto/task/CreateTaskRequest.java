package com.Ali_Choopani.Task_Management_System.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class CreateTaskRequest {

    @NotBlank(message = "Title can't be null or empty !")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters !")
    private String title;

    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @Future(message = "Due date must be a date in future !")
    @NotNull(message = "Due date can't be null or empty !")
    @JsonProperty("due date")
    private LocalDate dueDate;
}
