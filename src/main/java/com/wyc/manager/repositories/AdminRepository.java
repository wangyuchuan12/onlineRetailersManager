package com.wyc.manager.repositories;


import org.springframework.data.repository.CrudRepository;

import com.wyc.manager.domain.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long>{

    public Admin findByUsername(String username);

}
