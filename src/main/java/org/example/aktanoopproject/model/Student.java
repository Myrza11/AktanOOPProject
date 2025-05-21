package org.example.aktanoopproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Student extends User {
    @ElementCollection(targetClass = InterestType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "student_interests", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "interest")
    private Set<InterestType> interests;

    @Override
    public String getRole() {
        return "STUDENT";
    }
}
