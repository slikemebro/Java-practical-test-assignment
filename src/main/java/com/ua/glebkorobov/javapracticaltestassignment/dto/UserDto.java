package com.ua.glebkorobov.javapracticaltestassignment.dto;

import com.ua.glebkorobov.javapracticaltestassignment.annotations.MinAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    @MinAge
    private LocalDate birthday;

    private String address;

    private String phoneNumber;
}
