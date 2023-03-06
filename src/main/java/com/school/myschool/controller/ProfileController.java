package com.school.myschool.controller;

import com.school.myschool.model.Address;
import com.school.myschool.model.Person;
import com.school.myschool.model.Profile;
import com.school.myschool.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller("profileControllerBean")
public class ProfileController {

    @Autowired
    PersonRepository personRepository;


    @RequestMapping("/displayProfile")
    public ModelAndView displayProfile(Model model, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());
        if(person.getAddress()!=null && person.getAddress().getAddressId()>0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @RequestMapping(value = "/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile")Profile profile, Errors errors,HttpSession session){
        if(errors.hasErrors()){
            log.info("Submission failed due to "+errors.toString());
            return "profile.html";
        }
        Person person = (Person) session.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setMobileNumber(profile.getMobileNumber());
        person.setEmail(profile.getEmail());
        if(person.getAddress()==null || !(person.getAddress().getAddressId()>0)){
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        System.out.println(person.getAddress().getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        personRepository.save(person);
        session.setAttribute("loggedInPerson",person);
        System.out.println(profile);

        return  "redirect:/displayProfile";
    }
}
