package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.DialogSession;

public interface DialogSessionRepository extends CrudRepository<DialogSession, String>{

    DialogSession findByCustomerIdAndAdminId(String customerId, String adminId);

    Iterable<DialogSession> findAllByCustomerId(String customerId);

    Iterable<DialogSession> findAllByCustomerIdOrderByUpdateAtDesc(
            String customerId);

    Iterable<DialogSession> findAllByAdminId(String adminId);

}
