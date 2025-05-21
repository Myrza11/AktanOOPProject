package org.example.aktanoopproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    @NotBlank
    private String email;
    @Size(min = 6)
    private String password;
    private String telegramNickname;
    private String organisationName;
    private String organisationType;
    private String description;
    private String linkToSite;
    private String position;
}
