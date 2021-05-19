package com.example.creationtablesserver.controller;

import com.example.creationtablesserver.model.user.AuthorityUser;
import com.example.creationtablesserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService service;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/join")
    public String getJoin(@ModelAttribute("user") AuthorityUser authorityUser) {
        return "join";
    }

    @PostMapping("/join")
    public String createUser(@ModelAttribute("user") @Valid AuthorityUser authorityUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/join";

        service.join_user(authorityUser);
        return "redirect:/users";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }


}
