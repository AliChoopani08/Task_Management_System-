package com.Ali_Choopani.Task_Managment_System.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class LoginRequest {

    @NotBlank(message = "Email or phone number can't be empty or null !")
    @Pattern(regexp = "^(09\\d{9}|[A-Za-z0-9+_.-]+@gmail.com+)$", message = "You have to enter a valid email Or phone number !")
    @Schema(description = "Enter your saved email or phone number for identification")
    @JsonProperty("Email\\Phone Number")private String emailOrPhoneNumber;

    @NotBlank(message = "Password can't be empty or null !")
    private String password;

}
