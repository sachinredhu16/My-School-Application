package com.school.myschool.controller;

import com.school.myschool.model.Holiday;
import com.school.myschool.services.HolidayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
public class HolidayController {

    @Autowired
    HolidayServiceImpl holidayServiceImpl;
//
////    for query parameter
//    @GetMapping("/holidays")
//    public String displayHolidays(@RequestParam(required = false) boolean festival,
//                                  @RequestParam(required = false) boolean federal,Model model) {
//        model.addAttribute("festival",festival);
//        model.addAttribute("federal",federal);
//        List<Holiday> holidays = Arrays.asList(
//                new Holiday(" Jan 1 ","New Year's Day", Holiday.Type.FESTIVAL),
//                new Holiday(" Oct 31 ","Halloween", Holiday.Type.FESTIVAL),
//                new Holiday(" Nov 24 ","Thanksgiving Day", Holiday.Type.FESTIVAL),
//                new Holiday(" Dec 25 ","Christmas", Holiday.Type.FESTIVAL),
//                new Holiday(" Jan 17 ","Martin Luther King Jr. Day", Holiday.Type.FEDERAL),
//                new Holiday(" July 4 ","Independence Day", Holiday.Type.FEDERAL),
//                new Holiday(" Sep 5 ","Labor Day", Holiday.Type.FEDERAL),
//                new Holiday(" Nov 11 ","Veterans Day", Holiday.Type.FEDERAL)
//        );
//        Holiday.Type[] types = Holiday.Type.values();
//        for (Holiday.Type type : types) {
//            model.addAttribute(type.toString(),
//                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
//        }
//        return "holidays.html";
//
//    for query parameter
    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable(required = false) String display,Model model) {
        if(null!=display && display.equals("all")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }
        else if(null!=display && display.equals("festival")){
            model.addAttribute("festival",true);
        }
        else if(null!=display && display.equals("federal")){
            model.addAttribute("federal",true);
        }
        else {
            System.out.println("failure");
        }
        Iterable<Holiday> holidays = holidayServiceImpl.findALlHolidays();
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(),false).collect(Collectors.toList());
        for (Holiday holiday:holidayList){
            System.out.println(holiday);
        }
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
