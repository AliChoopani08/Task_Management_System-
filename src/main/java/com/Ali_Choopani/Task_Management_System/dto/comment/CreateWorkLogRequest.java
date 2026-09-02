package com.Ali_Choopani.Task_Management_System.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentRequest {

    @NotBlank(message = "Description of comment can't be null or empty !")
    private String description;
}
