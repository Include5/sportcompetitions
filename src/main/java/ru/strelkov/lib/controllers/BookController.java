package ru.strelkov.lib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.strelkov.lib.models.Book;
import ru.strelkov.lib.models.Person;
import ru.strelkov.lib.services.BooksService;
import ru.strelkov.lib.services.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "sort_by_year", required = false) boolean sortByYear,
            @RequestParam(value = "page", required = false) Integer pageNo,
            @RequestParam(value = "books_per_page", required = false) Integer pageSize) {

        if (pageNo != null && pageSize != null && sortByYear) {
            model.addAttribute("books", booksService.findPaginatedAndSorted(pageNo, pageSize));
        }
        if (pageNo != null && pageSize != null) {
            model.addAttribute("books", booksService.findPaginated(pageNo, pageSize));
        }
        else {
            model.addAttribute("books", booksService.findAll(sortByYear));
        }
        return "books/index";
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
        public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("book", booksService.searchOne(query));
        return "books/search";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("peopleList", peopleService.findAll());
        return "books/show";
    }

    @PatchMapping("/{id}/assign")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("peopleList") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.release(id);

        return "redirect:/books/" + id;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book ) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
        BindingResult bindingResult) {
    if (bindingResult.hasErrors())
        return "books/new";

    booksService.save(book);
    return "redirect:/books/" + book.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }


}
