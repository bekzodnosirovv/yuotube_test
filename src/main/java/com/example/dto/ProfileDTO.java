package com.example.dto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    @NotBlank(message = "Name field cannot be empty")
    private String name;
    @NotBlank(message = "Surname field cannot be empty")
    private String surname;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password field cannot be empty")
    @Size(min = 3,message = "Password must be at least 3 characters long")
    private String password;
    private String image_id;
    private AuthDTO image;
    @NotBlank(message = "Role field cannot be empty")
    private ProfileRole role;
    @NotBlank(message = "Status field cannot be empty")
    private ProfileStatus status;
    private String jwt;
}
