package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
public class AppHomeController {

    @GetMapping("/admin")
    public String showIndexPageAdmin() {
        return "index";
    }

    @GetMapping("/user")
    public String showIndexPageUser() {
        return "user";
    }
}
