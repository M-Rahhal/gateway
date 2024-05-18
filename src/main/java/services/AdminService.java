package services;


import entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repos.AdminRepository;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository repository;

    @Autowired

    public Admin getAdmin(String userName){
        Optional<Admin> obj = repository.findByUserName(userName);
        Admin admin = obj.isPresent()   ?   obj.get()   :   null;
        return admin;
    }
}
