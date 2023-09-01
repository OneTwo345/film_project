package com.example.film_project.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.film_project.service.category.CategoryService;
import com.example.film_project.service.person.PersonService;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class FilmController {

    private final PersonService personService;
    private final CategoryService categoryService;


    @GetMapping
    public String index(Model model){
        model.addAttribute("categories", categoryService.getPersonForSelect());
        model.addAttribute("directors", personService.getPersonsByRole("Director"));
        model.addAttribute("actors", personService.getPersonsByRole("Actor"));
        return "demo";
    }
}
