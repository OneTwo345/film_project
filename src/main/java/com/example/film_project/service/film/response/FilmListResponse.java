package com.example.film_project.service.film.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FilmListResponse {
    private Long id;

    private String name;

    private String director;

    private String actors;

    private String categories;

    private LocalDate publishDate;
}
