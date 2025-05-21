package org.example.aktanoopproject.dto;

import lombok.Data;

@Data
public class UpdateOrganisationDTO extends BaseUserUpdateDTO {

    private String organisationName;
    private String organisationType;
    private String description;
    private String linkToSite;
    private String position;
}