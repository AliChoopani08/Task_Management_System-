package com.Ali_Choopani.Task_Managment_System.dto;

import com.Ali_Choopani.Task_Managment_System.exceptions.InsufficientInformationException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.util.Optional.ofNullable;

@Builder
@Getter
@Setter
public class RegisterRequest {

    @Email(message = "Email must be a valid one !")
    private String email;

    @Pattern(regexp = "^$|09\\d{9}$", message = "Phone number must start with 09 and be 11 digits long !")
    private String phoneNumber;

    @NotBlank(message = "Password can't be empty or null !")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$%._*!?]{8,}$", message = "Password must have at least 1 upper character, lower character, number and one special character")
    private String password;


    @NotBlank(message = "You have to identify your role !")
    @Pattern(regexp = "(^ROLE_MANAGER|ROLE_DEVELOPER)$", message = "For role only (ROLE_MANAGER) Or (ROLE_DEVELOPER) are valid !")
    private String role;

    public RegisterRequest(String email, String phoneNumber, String password, String role) {
        if ((phoneNumber == null || phoneNumber.isBlank()) && (email == null || email.isBlank())) {
            throw new InsufficientInformationException();
        }
        ofNullable(email)
                .ifPresent(this::setEmail);
        ofNullable(phoneNumber)
                .ifPresent(this::setPhoneNumber);

        this.password = password;
        this.role = role;
    }
}
