package com.example.film_project.repository;

import com.example.film_project.domain.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query(value = "SELECT f FROM Film f " +
            "LEFT JOIN f.filmActors a ON f.id = a.film.id " +
            "LEFT JOIN f.filmCategories c ON f.id = c.film.id" +
            " where f.name like :search or " +
            "f.director.name like :search or " +
            "a.actor.name like :search or " +
            "c.category.name like :search GROUP BY f")
    Page<Film> searchEverythingIgnorePublishDate(String search, Pageable pageable);
}
