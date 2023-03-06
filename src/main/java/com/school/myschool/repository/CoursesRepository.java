package com.school.myschool.repository;

import com.school.myschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path = "Courses") // this is used for giving custom name to rest APi
//@RepositoryRestResource(exported = false) // this is used for disable the creation of APi of this repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    // example of static sorting
    List<Courses> findByOrderByNameDesc();
    List<Courses> findByOrderByName();

}
