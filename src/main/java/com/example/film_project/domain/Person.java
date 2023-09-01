package com.example.film_project.domain;


import com.example.film_project.domain.enums.EGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @OneToMany(mappedBy = "director")
    private List<Film> film;

    @OneToMany(mappedBy = "actor")
    private List<FilmActor> filmActors;

    @OneToMany(mappedBy = "person")
    private List<PersonRole> personRoles;

    public Person(Long id) {
        this.id = id;
    }


}
