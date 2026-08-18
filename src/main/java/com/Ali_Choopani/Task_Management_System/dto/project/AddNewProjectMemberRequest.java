package com.Ali_Choopani.Task_Management_System.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AddNewProjectMemberRequest {

    @NotBlank(message = "Member role can't be null or empty !")
    @Pattern(regexp = "^(ROLE_DEVELOPER|ROLE_VIEWER)$", message = "For member role only [ROLE_DEVELOPER] or [ROLE_VIEWER] are valid")
    @JsonProperty("Member role")
    private String memberRole;

}
