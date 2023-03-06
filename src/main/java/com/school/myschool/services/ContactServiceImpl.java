package com.school.myschool.services;

import com.school.myschool.constants.ApplicationConstant;
import com.school.myschool.model.Contact;
import com.school.myschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j // used for creating logging object
//@RequestScope // always create a new bean from every request
//@SessionScope // always create one bean for one user and one browser
@ApplicationScope // always create one bean for all
public class ContactServiceImpl implements ContactService{

    @Autowired
    ContactRepository repository;
    @Override
    public void saveMessageDetail(Contact contact){
        boolean isSaved = false;
        contact.setStatus(ApplicationConstant.OPEN);
        // this will be done automatically by AuditAwareImpl
        /*  contact.setCreatedBy(contact.getName());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        contact.setCreatedAt(timestamp.toLocalDateTime());*/

        Contact contactSaved = repository.save(contact);

        if(null!=contactSaved && contactSaved.getContactId()>0){
            isSaved= true;
        }
        System.out.println("Contact Object is saved !");
    }

    @Override
    public List<Contact> findMsgsWithOpenStatus() {
        return repository.findByStatus(ApplicationConstant.OPEN);
    }

    @Override
    public Page<Contact> findMsgsWithOpenStatus(int pageNum,  String sortField,String sortDir) {
        int pageSize =3;

        Pageable pageable = PageRequest.of(pageNum-1,pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending():
                        Sort.by(sortField).descending());
        Page<Contact> msgPage = repository.findByStatusWithQuery(ApplicationConstant.OPEN,pageable);


        return  msgPage;
    }

    @Override
    public void updateMsgStatus(int id) {

//        1. we can update by loading object and change it manually
//        Contact existed = repository.findById(id);
//        existed.setStatus(ApplicationConstant.CLOSED);
//        // this will be done automatically by AuditAwareImpl
//        /*existed.setUpdatedBy(name);
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        existed.setUpdatedAt(timestamp.toLocalDateTime());*/
//        repository.save(existed);
//        System.out.println("status is changed open to closed");

//        2. we can change status by using Query
        int updated = repository.updateStatusById(ApplicationConstant.CLOSED,id);
        if(updated>0){
            System.out.println("status is changed open to closed");
        }

    }
}
