package com.example.film_project.repository;

import com.example.film_project.domain.Film;
import com.example.film_project.domain.FilmActor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmActorRepository extends JpaRepository<FilmActor, Long> {
    void deleteByFilm(Film film);
}
