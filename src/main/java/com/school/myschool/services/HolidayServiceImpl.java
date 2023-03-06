package com.school.myschool.services;

import com.school.myschool.model.Holiday;
import com.school.myschool.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService{
    
    @Autowired
    HolidayRepository repository;
    
    @Override
    public Iterable<Holiday> findALlHolidays() {
        return  repository.findAll();
    }
}
