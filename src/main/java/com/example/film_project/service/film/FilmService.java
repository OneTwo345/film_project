package com.example.film_project.service.film;

import com.example.film_project.domain.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.film_project.repository.FilmActorRepository;
import com.example.film_project.repository.FilmCategoryRepository;
import com.example.film_project.repository.FilmRepository;
import com.example.film_project.service.film.request.FilmSaveRequest;
import com.example.film_project.service.film.response.FilmListResponse;
import com.example.film_project.utils.AppUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmCategoryRepository filmCategoryRepository;
    private final FilmActorRepository filmActorRepository;

    public Long create(FilmSaveRequest request){
        //cơm
//        Film film = new Film();
//        film.setName(request.getName());
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        film.setPublishDate(LocalDate.parse(request.getPublishDate(), format));
//        Person director = new Person(Long.valueOf(request.getDirector().getId()));
//        film.setDirector(director);
//        //lib
        var film = AppUtil.mapper.map(request, Film.class);
        film = filmRepository.save(film);
        var filmCategories = new ArrayList<FilmCategory>();

        for (String idCategory: request.getCategories()) {
            Category category = new Category(Long.valueOf(idCategory));
            filmCategories.add(new FilmCategory(film,category));
        }
        filmCategoryRepository.saveAll(filmCategories);

        var filmActors = new ArrayList<FilmActor>();

        for (String idActor: request.getActors()) {
            Person actor = new Person(Long.valueOf(idActor));
            filmActors.add(new FilmActor(actor,film));
        }
        filmActorRepository.saveAll(filmActors);
        return film.getId();
    }
    public void update(Long filmId, FilmSaveRequest request) {
        Optional<Film> optionalFilm = filmRepository.findById(filmId);

        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            AppUtil.mapper.map(request, film);
            film = filmRepository.save(film);

            // Xóa các bản ghi cũ trong bảng liên kết FilmCategory
            filmCategoryRepository.deleteByFilm(film);

            // Tạo danh sách FilmCategory mới và lưu vào bảng liên kết
            List<FilmCategory> filmCategories = new ArrayList<>();
            for (String idCategory : request.getCategories()) {
                Category category = new Category(Long.valueOf(idCategory));
                filmCategories.add(new FilmCategory(film, category));
            }
            filmCategoryRepository.saveAll(filmCategories);

            // Xóa các bản ghi cũ trong bảng liên kết FilmActor
            filmActorRepository.deleteByFilm(film);

            // Tạo danh sách FilmActor mới và lưu vào bảng liên kết
            List<FilmActor> filmActors = new ArrayList<>();
            for (String idActor : request.getActors()) {
                Person actor = new Person(Long.valueOf(idActor));
                filmActors.add(new FilmActor(actor, film));
            }
            filmActorRepository.saveAll(filmActors);
        } else {
            throw new RuntimeException("Film not found with ID: " + filmId);
        }
    }

    public Page<FilmListResponse> getAll(Pageable pageable, String search) {
        search = "%"+search+"%";
        return filmRepository.searchEverythingIgnorePublishDate(search, pageable).map(film -> FilmListResponse.builder()
                .id(film.getId())
                .name(film.getName())
                .director(film.getDirector().getName())
                .publishDate(film.getPublishDate())
                .actors(film.getFilmActors()
                        .stream()
                        .map(filmActor -> filmActor.getActor().getName())
                        .collect(Collectors.joining(", ")))
                .categories(film.getFilmCategories()
                        .stream()
                        .map(filmCategory -> filmCategory.getCategory().getName())
                        .collect(Collectors.joining(", "))).build());
    }

    @Transactional
    public void delete(Long filmId) {

        Optional<Film> optionalFilm = filmRepository.findById(filmId);

        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            filmCategoryRepository.deleteByFilm(film);
            filmActorRepository.deleteByFilm(film);
            filmRepository.delete(film);
        } else {
            throw new RuntimeException("Film not found with ID: " + filmId);
        }
    }
}
