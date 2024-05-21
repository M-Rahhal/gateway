package com.streamPatform.gateway.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.streamPatform.gateway.services.AdminService;
import com.streamPatform.gateway.utils.annotations.PublicAPI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.streamPatform.gateway.services.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;
    @PublicAPI
    @PostMapping("/login")
    public ResponseEntity login(@JsonProperty("user_name") String userName , @JsonProperty("password") String password){
       try {
           String token = authService.login(userName, password);
           return ResponseEntity.ok().header("Authorization" , token).build();
       }catch (Exception e){
           return ResponseEntity.badRequest().body("Invalid Password");
       }
    }

    @PostMapping("/register")
    public ResponseEntity registerAdmin(
            @JsonProperty("user_name") String userName ,
            @JsonProperty("password") String password ,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            HttpServletRequest request
            ){
            String key = request.getHeader("KEY");
            if (!key.equals("KEY"))
                return ResponseEntity.badRequest().body("Unauthorized");
            adminService.addAdmin(userName , password ,firstName , lastName);
            return ResponseEntity.ok().body("New Admin Registered");

    }

}
