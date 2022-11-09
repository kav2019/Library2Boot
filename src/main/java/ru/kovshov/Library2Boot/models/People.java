package ru.kovshov.Library2Boot.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "people")
public class People {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "name")
    @NotEmpty(message = "should not be empty")
    @Size(min = 3, max = 30, message = "in the range from 3 to 30")
    private String name;

    @Column(name = "yearborn")
    @Min(value = 0, message = "can't be less than zero")
    private int yearBorn;

    @ManyToMany
    @JoinTable(
            name = "user_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonIgnore
    private List<Book> books;


    public People(String name, int yearBorn) {
        this.name = name;
        this.yearBorn = yearBorn;
    }

    public People() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "name: " + name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode()==obj.hashCode();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
