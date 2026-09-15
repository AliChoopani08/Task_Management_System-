package com.Ali_Choopani.Task_Management_System.dto.user.profile;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class UpdateProfileFieldsRequest implements ProfileFields {

    private String firstName;

    private String surname;

    @Past(message = "Birth date must be a date in the past")
    private LocalDate birthDate;
    @Size(max = 100, message = "Biography must be less than 100 characters !")
    private String biography;
}
