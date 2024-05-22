package com.streamPatform.gateway.services;


import com.streamPatform.gateway.entity.Admin;
import com.streamPatform.gateway.utils.annotations.services.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.streamPatform.gateway.repos.AdminRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository repository;


    @Autowired
    private CryptoService cryptoService;


    public Admin getAdmin(String userName){
        Optional<Admin> obj = repository.findByUserName(userName);
        Admin admin = obj.isPresent()   ?   obj.get()   :   null;
        return admin;
    }
    public void addAdmin(String username , String password,String firstName, String lastName ) throws Exception {
        Admin admin = null;
        try {
             admin = repository.findByUserName(username).get();
             throw new Exception("User already defined!");
        }
        catch (NoSuchElementException e){
            admin = new Admin();
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setPassword(cryptoService.Hash(password));
            admin.setUserName(username);
            repository.save(admin);
        }


    }
}
