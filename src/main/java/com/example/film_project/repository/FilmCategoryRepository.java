package com.example.film_project.repository;

import com.example.film_project.domain.Film;
import com.example.film_project.domain.FilmCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilmCategoryRepository extends JpaRepository<FilmCategory, Long> {
void deleteByFilm(Film film);
}
