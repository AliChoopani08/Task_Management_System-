package com.Ali_Choopani.Task_Management_System.dto.project;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class CreateProjectRequest {

    @NotBlank(message = "Title of project can't be empty or null !")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters !")
    private String title;

    @Size(max = 1000, message = "description must be less than 1000 characters !")
    private String description;

    @NotNull(message = "Due date can't be empty or null !")
    @Future(message = "Due date must be a date in future !")
    private LocalDate dueDate;

}
