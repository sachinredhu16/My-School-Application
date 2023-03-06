package com.school.myschool.services;

import com.school.myschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ContactService {
    void saveMessageDetail(Contact contact);
    List<Contact> findMsgsWithOpenStatus();

    Page<Contact> findMsgsWithOpenStatus(int pageNum,  String sortField,String sortDir);

    void updateMsgStatus(int id);
}
