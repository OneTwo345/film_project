package com.example.film_project.controller.rest;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.film_project.service.film.FilmService;
import com.example.film_project.service.film.request.FilmSaveRequest;
import com.example.film_project.service.film.response.FilmListResponse;


@RestController
@RequestMapping(value = "/api/films")
@AllArgsConstructor
public class FilmRestController {

    private final FilmService filmService;


    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid FilmSaveRequest request) {

        return new ResponseEntity<>(filmService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<FilmListResponse>> list(@PageableDefault(size = 5) Pageable pageable,
                                                       @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(filmService.getAll(pageable, search), HttpStatus.OK);
    }

    @PutMapping("/{filmId}")
    public ResponseEntity<Void> update(
            @PathVariable Long filmId,
            @RequestBody @Valid FilmSaveRequest request
    ) {
        filmService.update(filmId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{filmId}")
    public ResponseEntity<Void> delete(@PathVariable Long filmId) {
        filmService.delete(filmId);
        return ResponseEntity.noContent().build();
    }

}

