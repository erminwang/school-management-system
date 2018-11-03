package com.erminray.polls.model.system;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "semester")
public class Semester {
    @Id
    private String name;
    @NotNull
    private Instant startDate;
    @NotNull
    private Instant endDate;
    @OneToMany(
        mappedBy = "semester",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Course> courses;

}
