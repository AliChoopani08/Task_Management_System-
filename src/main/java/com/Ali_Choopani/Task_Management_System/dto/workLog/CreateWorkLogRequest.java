package com.Ali_Choopani.Task_Management_System.dto.workLog;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CreateWorkLogRequest {

    @NotBlank(message = "Description of comment can't be null or empty !")
    private String description;
}
