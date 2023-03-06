package com.school.myschool.services;

import com.school.myschool.constants.ApplicationConstant;
import com.school.myschool.model.Person;
import com.school.myschool.model.Roles;
import com.school.myschool.repository.PersonRepository;
import com.school.myschool.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public boolean createPerson(Person person) {
        boolean isSaved =false;
        Roles roles = rolesRepository.findByRoleName(ApplicationConstant.STUDENT_ROLE);
        person.setRoles(roles);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if(person != null && person.getPersonId()>0){
            isSaved=true;
        }
        return isSaved;
    }
}
