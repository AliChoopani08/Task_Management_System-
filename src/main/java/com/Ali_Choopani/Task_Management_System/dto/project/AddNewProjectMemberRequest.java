package com.Ali_Choopani.Task_Management_System.dto.project;

import com.Ali_Choopani.Task_Management_System.entities.ProjectRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AddNewProjectMemberRequest {

    @NotBlank(message = "Member role can't be null or empty !")
    private ProjectRole memberRole;

}
