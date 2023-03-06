package com.school.myschool.services;

import com.school.myschool.model.Courses;
import com.school.myschool.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class coursesServiceImpl implements CoursesService {
    @Autowired
    CoursesRepository coursesRepository;

    @Override
    public boolean addNewCourse(Courses course) {
        boolean isSaved = false;
        Courses saved = coursesRepository.save(course);
        if(saved!=null && saved.getCourseId()>0){
            isSaved= true;
        }
        return  isSaved;
    }
}
