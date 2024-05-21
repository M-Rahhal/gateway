package com.streamPatform.gateway.services;


import com.streamPatform.gateway.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.streamPatform.gateway.repos.AdminRepository;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository repository;


    public Admin getAdmin(String userName){
        Optional<Admin> obj = repository.findByUserName(userName);
        Admin admin = obj.isPresent()   ?   obj.get()   :   null;
        return admin;
    }
    public void addAdmin(String username , String password,String firstName, String lastName ){
        Admin admin = new Admin();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPassword(password);
        admin.setUserName(username);
        repository.save(admin);
    }
}
