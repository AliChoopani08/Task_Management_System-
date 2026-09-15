package com.Ali_Choopani.Task_Management_System.dto.user.profile;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class CompleteProfileFieldsRequest implements ProfileFields {

    @NotBlank(message = "First name can't be null or empty !")
    private String firstName;

    @NotBlank(message = "Surname can't be null or empty !")
    private String surname;

    @Past(message = "Birth date must be a date in the past")
    private LocalDate birthDate;
    @Size(max = 100, message = "Biography must be less than 100 characters !")
    private String biography;
}
