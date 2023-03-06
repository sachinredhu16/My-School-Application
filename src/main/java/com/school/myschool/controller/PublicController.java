package com.school.myschool.controller;

import com.school.myschool.model.Person;
import com.school.myschool.services.PersonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    PersonServiceImpl personServiceImpl;


    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String displayRegisterPage(Model model){
        model.addAttribute("person",new Person());
        return "register.html";
    }

    @RequestMapping(value = "/createUser",method =POST)
    public String displayRegisterPage(@Valid @ModelAttribute("person") Person person, Errors errors){

        if(errors.hasErrors()){
            log.info("Submission failed due to "+errors.toString());
            return "register.html";
        }
        boolean saved = personServiceImpl.createPerson(person);
        if(saved){
            return "redirect:/login?register=true";
        }
        else {
            return "register.html";
        }

    }
}
