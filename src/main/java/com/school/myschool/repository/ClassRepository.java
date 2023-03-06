package com.school.myschool.repository;

import com.school.myschool.model.EazyClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<EazyClass,Integer> {
}
