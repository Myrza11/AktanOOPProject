package org.example.aktanoopproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @ElementCollection(targetClass = Activity.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "post_activities", joinColumns = @JoinColumn(name = "organisation_id"))
    @Column(name = "interest")
    private Set<Activity> activities = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
}
