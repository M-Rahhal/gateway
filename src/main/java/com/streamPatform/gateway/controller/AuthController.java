package com.streamPatform.gateway.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.streamPatform.gateway.requests.LoginRequest;
import com.streamPatform.gateway.requests.RegisterRequest;
import com.streamPatform.gateway.services.AdminService;
import com.streamPatform.gateway.utils.annotations.PublicAPI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
       try {
           String token = authService.login(loginRequest.userName(), loginRequest.password());
           return ResponseEntity.ok().header("Authorization" , token).build();
       }catch (Exception e){
           return ResponseEntity.badRequest().body("Invalid Password");
       }
    }

    @PublicAPI
    @PostMapping("/register")
    public ResponseEntity registerAdmin(
            @RequestBody RegisterRequest registerRequest,
            HttpServletRequest request
            ){

            String key = request.getHeader("KEY");
            if (!key.equals("KEY"))
                return ResponseEntity.badRequest().body("Unauthorized");
        try {
            adminService.addAdmin(registerRequest.userName() , registerRequest.password() ,registerRequest.firstName() , registerRequest.lastName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("New Admin Registered");

    }

}
