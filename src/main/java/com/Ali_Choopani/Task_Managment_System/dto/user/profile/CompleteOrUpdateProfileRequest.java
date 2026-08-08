package com.Ali_Choopani.Task_Managment_System.dto.user.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class CompleteOrUpdateProfileRequest {

    @NotBlank(message = "First name can't be null or empty !")
    @JsonProperty("First name")
    private String firstName;

    @NotBlank(message = "Surname can't be null or empty !")
    @JsonProperty("Surname")
    private String surname;

    @Past(message = "Birth date must be a date in the past")
    private LocalDate birthDate;
    @Size(max = 100, message = "Biography must be less than 100 characters !")
    private String biography;
}
