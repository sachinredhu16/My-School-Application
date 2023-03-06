package com.school.myschool.controller;

import com.school.myschool.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("student")
public class StudentController {

    @RequestMapping("displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView("courses_enrolled");
        Person person = (Person) httpSession.getAttribute("loggedInPerson");
        modelAndView.addObject("person",person);
        return modelAndView;

    }
}
