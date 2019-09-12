package com.example.bettingGame.core.api.v1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {


    @RequestMapping(value = "/index")
    public String indexPage() {
        return "index";
    }


    @RequestMapping(value = "/adminka")
    public String adminPage() {
        return "adminka";
    }

    @RequestMapping(value = "/userRanking")
    public String userRankingPage() {
        return "userRanking";
    }

//    @RequestMapping(value = "/sign-up")
//    public String registrationPage() {
//        return "registration";
//    }
}
