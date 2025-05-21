package org.example.aktanoopproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.aktanoopproject.model.InterestType;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
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

    private Set<String> interests;
}