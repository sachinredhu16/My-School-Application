package com.school.myschool.controller;

import com.school.myschool.model.Person;
import com.school.myschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {
    @Autowired
    PersonRepository personRepository;
    @RequestMapping(value = "/dashboard")
    public String showDashboard(Model model, Authentication authentication, HttpSession session) throws Exception {
        Person person = personRepository.readByEmail(authentication.getName());

        model.addAttribute("username",person.getName());
        model.addAttribute("roles",authentication.getAuthorities().toString());

        if(person.getEazyClass()!=null && null!=person.getEazyClass().getName()){
            model.addAttribute("enrolledClass",person.getEazyClass().getName());
        }

        session.setAttribute("loggedInPerson",person);
//        throw new Exception("Not able to find what you search");
        return "dashboard.html";
    }
}
