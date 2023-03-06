package com.school.myschool.repository;

import com.school.myschool.model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HolidayRepository extends CrudRepository<Holiday,String> {
}
