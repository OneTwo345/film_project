package com.example.film_project.service.category;

import com.example.film_project.service.response.SelectOptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.film_project.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;


    public List<SelectOptionResponse> getPersonForSelect(){
        return repository.findAll()
                .stream()
                .map(element -> new SelectOptionResponse(element.getId().toString(), element.getName()))
                .collect(Collectors.toList());
    }
}
