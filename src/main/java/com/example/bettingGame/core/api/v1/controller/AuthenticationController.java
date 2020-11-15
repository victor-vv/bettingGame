package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {

    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/login")
    public String login() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
        return "Authenticated successfully";
    }

    @GetMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

//    @PostMapping(value = "/register")
//    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
//        ModelAndView modelAndView = new ModelAndView();
//        if (bindingResult.hasErrors()) {
//            modelAndView.addObject("successMessage", "Something wrong in the form");
//            modelMap.addAttribute("bindingResult", bindingResult);
//        } else {
//            userService.createUser(user);
//            modelAndView.addObject("successMessage", "Вы успешно зарегистрировались!");
//        }
//        modelAndView.addObject("user", new User());
//        modelAndView.setViewName("register");
//        return modelAndView;
//    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/register")
    public void registerUser(@RequestBody User user) {
        userService.createUser(user);
    }
}
