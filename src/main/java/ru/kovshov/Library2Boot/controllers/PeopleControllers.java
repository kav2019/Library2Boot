package ru.kovshov.Library2Boot.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kovshov.Library2Boot.models.People;
import ru.kovshov.Library2Boot.service.PeopleService;

@Controller
@RequestMapping("/library")
public class PeopleControllers {
    private final PeopleService peopleService;

    @Autowired
    public PeopleControllers(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping() //показать всех пользователей
    public String allUsers(Model model){
        model.addAttribute("people", peopleService.returnAllPeople());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        model.addAttribute("nameuser", name);
        return "people/all_users";
    }

    @GetMapping("/{id}") //показать одного пользователя
    public String getOnePeople(@PathVariable("id") int id, Model model){
        model.addAttribute("people", peopleService.returnOnePeople(id));
        model.addAttribute("books", peopleService.bookListUsing(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        model.addAttribute("nameuser", name);
        return "people/one_user";
    }

    @GetMapping("/{id}/edit")  //показать страницу с полями для изменения юзера
    public String editPeople(Model model, @PathVariable("id") int id){
        model.addAttribute("people", peopleService.returnOnePeople(id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        model.addAttribute("nameuser", name);
        return "people/edit_users";
    }

    @PatchMapping("{id}") //сохранение измененного пользователя с редиректом
    public String saveEdit(@PathVariable("id") int id,  @ModelAttribute("people") People people){
        peopleService.saveChangePeople(people, id);
        return "redirect:/library";
    }

    @GetMapping("/new_people") //страницу создания пользователя
    public String addUsers(@ModelAttribute("people") People people){
        return "people/add_user";
    }

    @PatchMapping("/new_people")
    public String saveAddUser(@ModelAttribute("people") People people){ //сохраняем ового пользователя и редирект на главную страницу
        peopleService.savePeople(people);
        return "redirect:/library";
    }

    @GetMapping("/{id}/del") //даляем человека
    public String delUser(@PathVariable("id") int id){
        peopleService.delPeople(id);
        return "redirect:/library";
    }

    @GetMapping("/{idP}/del_book/{idB}")
    public String delBook(@PathVariable("idB") int idBook, @PathVariable("idP")int idPeople){ //удаляем книгу из списка
        peopleService.delBookOfList(idPeople, idBook);
        return "redirect:/library/"+idPeople;
    }
}
