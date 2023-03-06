package com.school.myschool.services;

import com.school.myschool.model.EazyClass;
import com.school.myschool.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements ClassService{

    @Autowired
    ClassRepository classRepository;
    @Override
    public boolean addNewClass(EazyClass eazyClass) {
        boolean isSaved = false;
        EazyClass saved = classRepository.save(eazyClass);

        if(saved!=null && saved.getClassId()>0){
            isSaved = true;
        }
        return isSaved;
    }
}
