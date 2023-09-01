package com.example.film_project.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "film_actors")
public class FilmActor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Person actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;
    public FilmActor(Person actor, Film film) {
        this.actor = actor;
        this.film = film;
    }

}
