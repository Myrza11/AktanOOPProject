package org.example.aktanoopproject.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateStudentDTO extends BaseUserUpdateDTO {
    private Set<String> interests;
}
