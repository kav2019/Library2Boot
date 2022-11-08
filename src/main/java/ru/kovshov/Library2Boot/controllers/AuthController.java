package ru.kovshov.Library2Boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kovshov.Library2Boot.models.User;
import ru.kovshov.Library2Boot.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user")User user){
        return "/auth/registration";
    }

    @PreAuthorize("has")
    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user")User user){
        if(userService.saveNewUser(user)){
            return "redirect:/auth/login";
        }
        return "redirect:/auth/registration";
    }
}
