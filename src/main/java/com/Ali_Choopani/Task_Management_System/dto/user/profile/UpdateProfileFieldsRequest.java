package com.Ali_Choopani.Task_Management_System.dto.user.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class UpdateProfileFieldsRequest implements ProfileFieldsMapper{

    @JsonProperty("First name")
    private String firstName;

    @JsonProperty("Surname")
    private String surname;

    @Past(message = "Birth date must be a date in the past")
    private LocalDate birthDate;
    @Size(max = 100, message = "Biography must be less than 100 characters !")
    private String biography;
}
