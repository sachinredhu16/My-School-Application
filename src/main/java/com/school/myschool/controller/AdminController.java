package com.school.myschool.controller;

import com.school.myschool.model.Courses;
import com.school.myschool.model.EazyClass;
import com.school.myschool.model.Person;
import com.school.myschool.repository.ClassRepository;
import com.school.myschool.repository.CoursesRepository;
import com.school.myschool.repository.PersonRepository;
import com.school.myschool.services.ClassService;
import com.school.myschool.services.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ClassService classService;
    @Autowired
    ClassRepository classRepository;

    @Autowired
    PersonRepository personRepository;
    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    CoursesService coursesService;

    // All work related to class
    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        ModelAndView modelAndView = new ModelAndView("classes.html");
        List<EazyClass> eazyClasses = (List<EazyClass>) classRepository.findAll();
        modelAndView.addObject("eazyClass", new EazyClass());
        model.addAttribute("eazyClasses", eazyClasses);
        return modelAndView;
    }

    @RequestMapping("/addNewClass")
    public String addNewClass(@Valid @ModelAttribute("eazyClass") EazyClass eazyClass, Errors errors) {
        if (errors.hasErrors()) {
            log.info("Errors is :- " + errors.toString());
            return "classes.html";
        }
        boolean isSaved = classService.addNewClass(eazyClass);
        return "redirect:/admin/displayClasses?classAdded=true";

    }

    @RequestMapping("deleteClass")
    public ModelAndView deleteClass(@RequestParam int id) {
        Optional<EazyClass> eazyClass = classRepository.findById(id);
        for (Person person : eazyClass.get().getPersons()) {
            person.setEazyClass(null);
            personRepository.save(person);
        }

        classRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses?isDeleted=true");
        return modelAndView;
    }

    // All work related to Students and Class
    @RequestMapping("/displayStudents")
    public ModelAndView addStudent(Model model, @RequestParam int classId, HttpSession session, @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;

        Optional<EazyClass> eazyClass = classRepository.findById(classId);
        ModelAndView modelAndView = new ModelAndView("students.html");
        modelAndView.addObject("eazyClass", eazyClass.get());
        modelAndView.addObject("person", new Person());
        if (error != null) {
            errorMessage = "Invalid e-mail. Please enter a valid e-mail !";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        session.setAttribute("eazyClass", eazyClass.get());
        return modelAndView;
    }

    @RequestMapping("/addStudent")
    public ModelAndView addStudent(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId() + "&error=true");
            return modelAndView;
        }
        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        classRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId());
        return modelAndView;

    }

    @RequestMapping("/deleteStudent")
    public ModelAndView deleteStudent(@RequestParam int personId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setEazyClass(null);
        personRepository.save(person.get());
        eazyClass.getPersons().remove(person.get());
        EazyClass eazyClassSaved = classRepository.save(eazyClass);
        session.setAttribute("eazyClass", eazyClassSaved);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId());
        return modelAndView;
    }

    // All work related to Course
    @RequestMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {
        // example of static sorting
        // List<Courses> courses = coursesRepository.findByOrderByNameDesc();
        // example of dynamic sorting
        List<Courses> courses = coursesRepository.findAll(Sort.by("name").ascending().and(Sort.by("fees")));
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("course", new Courses());
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

        @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model,@ModelAttribute("course") Courses course,Errors errors){
//        if(errors.hasErrors()){
//            log.info("Error is :-"+errors);
//           return  "courses_secure.html";
//        }
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(course.getName());
        System.out.println(course.getFees());
        boolean isSaved = coursesService.addNewCourse(course);
        modelAndView.setViewName("redirect:/admin/displayCourses?courseAdded=true");
        return modelAndView;

    }

    @RequestMapping("/viewStudents")
    public ModelAndView viewStudents(Model model,@RequestParam int id,HttpSession httpSession,@RequestParam(value = "error",required = false) String error){
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        String  errorMessage= null;
        if (error != null) {
            errorMessage = "Invalid e-mail. Please enter a valid e-mail !";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        Optional<Courses> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person",new Person());
        httpSession.setAttribute("courses",courses.get());
        return modelAndView;
    }

    @RequestMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(@ModelAttribute("person")Person person,HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) httpSession.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()+"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        personRepository.save(personEntity);
        courses.getPersons().add(personEntity);
        coursesRepository.save(courses);
        httpSession.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }
    @RequestMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam(name = "personId") int id,HttpSession httpSession ){
        ModelAndView modelAndView = new ModelAndView();
        Optional<Person> person = personRepository.findById(id);
        Courses courses = (Courses) httpSession.getAttribute("courses");
        courses.getPersons().remove(person);
        coursesRepository.save(courses);
        person.get().getCourses().remove(courses);
        personRepository.save(person.get());
        httpSession.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()+"&studentDeleted=true");
        return modelAndView;

    }

}
