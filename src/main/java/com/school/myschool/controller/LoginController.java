package com.school.myschool.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class LoginController {

    @RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error",required = false)String error,
                                   @RequestParam(value = "logout",required = false)String logout,@RequestParam(value = "register",required = false)String register, Model model){
        String alertUserPassFail = null;
        String alertLogoutSuccess = null;
        String alertRegisterSuccess= null;
        if(error!=null){
            alertUserPassFail= "Username and Password is incorrect . Please check !";
        }
        if(logout!=null){
            alertLogoutSuccess="You have been successfully logged out !";
        }
        if(register!=null){
            alertRegisterSuccess="Registration successfully . Please login below !";
        }
        model.addAttribute("alertUserPassFail",alertUserPassFail);
        model.addAttribute("alertLogoutSuccess",alertLogoutSuccess);
        model.addAttribute("alertRegisterSuccess",alertRegisterSuccess);
        return "login.html";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "redirect:/login?logout=true";
    }
}
