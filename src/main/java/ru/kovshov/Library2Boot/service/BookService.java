package ru.kovshov.Library2Boot.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kovshov.Library2Boot.exeption.NotFoundFromDBExeption;
import ru.kovshov.Library2Boot.models.Book;
import ru.kovshov.Library2Boot.models.People;
import ru.kovshov.Library2Boot.repository.BookRpository;
import ru.kovshov.Library2Boot.repository.PeopleRepository;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRpository bookRpository;
    private final PeopleService peopleService;
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public BookService(BookRpository bookRpository, PeopleRepository peopleRepository, PeopleService peopleService, EntityManager entityManager) {
        this.bookRpository = bookRpository;
        this.peopleService = peopleService;
        this.entityManager = entityManager;
    }

    //метод возвращающий все книги
    public List<Book> returnAllBook(){
        return bookRpository.findAll();
    }

    public Page<Book> returnBookOnPage(int page, int booksPerPage){
        return bookRpository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year")));
    }

    //метод возвращающий одну книгу
    public Book returnOneBook(int id){
//        return bookRpository.findById(id).orElse(null);
        return bookRpository.findById(id).orElseThrow(NotFoundFromDBExeption::new);
    }

    //метод добавляющий книгу
    @Transactional
    public void saveBook(Book book){
        bookRpository.save(book);
    }

    //метод сохраняющий изменеия
    @Transactional
    public void saveChangeBook(Book book, int id){
        book.setId(id);
        bookRpository.save(book);
    }

    //метод удляющий книгу
    @Transactional
    public void delBook(int id){
        bookRpository.deleteById(id);
    }

    //метод показывающий у книги читателей
    public List<People> getReadBook(int id){
        Book book = bookRpository.findById(id).get();
        List<People> peopleList = book.getPeoples();
        Hibernate.initialize(peopleList);
        return peopleList;
    }

    //добавляет книгу к пользователю
    @Transactional
    public void setReadPeople(int idUsers, int idBook){
        Book book = bookRpository.findById(idBook).get();
        People people = peopleService.returnOnePeople(idUsers);
        people.getBooks().add(book);
    }

    public List<Book> search(String name){
        String sql = "select b from Book as b where UPPER(b.name) LIKE UPPER('%" +name+"%')";
        return entityManager.createQuery(sql).getResultList();
    }
}
