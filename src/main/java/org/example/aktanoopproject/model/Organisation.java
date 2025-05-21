package org.example.aktanoopproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Organisation extends User {
    private String organisationName;
    private String organisationType;
    private String description;
    private String linkToSite;
    private String position;

    @Override
    public String getRole() {
        return "ORGANISATION";
    }
}
