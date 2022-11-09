package ru.kovshov.Library2Boot.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kovshov.Library2Boot.exeption.NotCreateBedRequest;
import ru.kovshov.Library2Boot.exeption.NotFoundFromDBExeption;
import ru.kovshov.Library2Boot.models.Book;
import ru.kovshov.Library2Boot.models.People;
import ru.kovshov.Library2Boot.service.BookService;
import ru.kovshov.Library2Boot.service.PeopleService;
import ru.kovshov.Library2Boot.util.ErrorsResponse;

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



    @GetMapping("/people")
    public List<People> allPeople(){
        return peopleService.returnAllPeople();
    }

    @GetMapping("/people/{id}")
    public People getPeopleById(@PathVariable("id") int id){
        return peopleService.returnOnePeople(id);
    }



    @PostMapping("/people")
    public ResponseEntity<HttpStatus> saveBook(@RequestBody @Valid People people,
                                               BindingResult bindingResult){
        checkError(bindingResult);
        peopleService.savePeople(people);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<HttpStatus> saveBook(@RequestBody @Valid Book book, BindingResult bindingResult){
        checkError(bindingResult);
        bookService.saveBook(book);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorsResponse> handler(NotFoundFromDBExeption e){
        ErrorsResponse response = new ErrorsResponse("record by id not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorsResponse> handler(NotCreateBedRequest e){
        ErrorsResponse response = new ErrorsResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private void checkError(BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            StringBuilder msg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                msg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new NotCreateBedRequest(msg.toString());
        }
    }
}
