package org.example.aktanoopproject.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class BaseUserUpdateDTO {
    private String name;

    private String surname;

    @Size(min = 6)
    private String password;

    private String telegramNickname;
}
