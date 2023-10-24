package com.school.myschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @RequestMapping(value = {"", "/", "home"})
    public String displayHomePage() {

        return "home.html";
    }
    @RequestMapping("/courses")
    public String displayCourses() {

//        return new RedirectView("http://localhost:9091/home");
        return "courses.html";
    }

}
