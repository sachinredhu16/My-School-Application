package com.school.myschool.repository;

import com.school.myschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {
    Contact findById(int id);
    List<Contact> findByStatus(String status);

    //Below query is in JPQL
//    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    //Below query is in SQL because we use nativeQuery = true . this denotes that this query contains SQL query
    @Query(value = "SELECT * FROM contact_msg c WHERE c.status = :status",nativeQuery = true)
    Page<Contact> findByStatusWithQuery(@Param("status") String status , Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);

    //NamedQuery
    Page<Contact> findOpenMsgs(@Param("status") String status, Pageable pageable);
    //NamedQuery
    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);

    //NamedNativeQuery
    @Query(nativeQuery = true)
    Page<Contact> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

    //NamedNativeQuery
    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int id);
}
