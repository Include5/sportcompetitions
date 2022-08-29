package ru.strelkov.sportcompetitions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.strelkov.sportcompetitions.services.DisciplineService;
import ru.strelkov.sportcompetitions.models.Discipline;

import javax.validation.Valid;

@Controller
@RequestMapping("/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    @Autowired
    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("disciplines", disciplineService.findAll());

        return "disciplines/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("discipline") Discipline discipline) {
        model.addAttribute("discipline", disciplineService.findOne(id));

        return "disciplines/show";
    }

    @GetMapping("/new")
    public String newTeam(@ModelAttribute("discipline") Discipline discipline) {
        return "disciplines/new";
    }

    @PostMapping
    public String create(@ModelAttribute("discipline") @Valid Discipline discipline,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "disciplines/new";

        disciplineService.save(discipline);
        return "redirect:/disciplines/" + discipline.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("discipline", disciplineService.findOne(id));

        return "disciplines/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("discipline") @Valid Discipline discipline, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "disciplines/edit";

        disciplineService.update(id, discipline);
        return "redirect:/disciplines/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        disciplineService.delete(id);

        return "redirect:/disciplines";
    }
}
