package com.school.myschool.controller;

import com.school.myschool.constants.ApplicationConstant;
import com.school.myschool.model.Contact;
import com.school.myschool.services.ContactServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@Slf4j
public class ContactController {
    @Autowired
    ContactServiceImpl contactServiceimpl;


    @RequestMapping("/contact")
    public String displayContactPage(@RequestParam(value = "submitted" ,required = false)String submitted,Model model){
        String sub_msg = null;
        if(submitted!=null){
            sub_msg = "We will contact you soon. Thank you!";
        }
        model.addAttribute("sub_msg",sub_msg);
        model.addAttribute("contact",new Contact());
        return "contact.html";
    }

/*   1. @ModelAttribute is used to bind the contact object which come from UI to
        new object for performing the controller and service action
     2. @Valid is used to declaring validation which is declared in contact.java using annotations
     3. If some error occurred during submission then they are stored into Errors object and pass them to UI
*/
    @RequestMapping(value = "/saveMsg",method=POST)
    public String saveContact(@Valid @ModelAttribute("contact") Contact contact, Errors errors){

        if(errors.hasErrors()){
            log.info("Submission failed due to "+errors.toString());
            return "contact.html";
        }

        contactServiceimpl.saveMessageDetail(contact);
        return "redirect:/contact?submitted=true";
    }

    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model, @PathVariable(name = "pageNum") int pageNum, @RequestParam(name = "sortField") String sortField, @RequestParam(name = "sortDir") String sortDir){
        Page<Contact>  msgPage= contactServiceimpl.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",msgPage.getTotalPages());
        model.addAttribute("totalMsgs",msgPage.getTotalElements());
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
        for(Contact contact:contactMsgs){
            System.out.println(contact);
        }
        model.addAttribute("contactMsgs",contactMsgs);
        return modelAndView;
    }


    @RequestMapping("/closeMsg")
    public String closeMsg(@RequestParam int id ){
        contactServiceimpl.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }
}
