package ru.kovshov.Library2Boot.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kovshov.Library2Boot.models.Book;
import ru.kovshov.Library2Boot.models.People;
import ru.kovshov.Library2Boot.service.BookService;
import ru.kovshov.Library2Boot.service.PeopleService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public RestController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping("/book")
    public List<Book> allBook(){
        return bookService.returnAllBook();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable("id") int id){
        return bookService.returnOneBook(id);
    }

    @PostMapping("/book")
    public Book saveBook(@RequestBody Book book){
        bookService.saveBook(book);
        return book;
    }

    @GetMapping("/people")
    public List<People> allPeople(){
        return peopleService.returnAllPeople();
    }

    @GetMapping("/people/{id}")
    public People getPeopleById(@PathVariable("id") int id){
        return peopleService.returnOnePeople(id);
    }

    @PostMapping("/people")
    public People saveBook(@RequestBody People people){
        peopleService.savePeople(people);
        return people;
    }
}
