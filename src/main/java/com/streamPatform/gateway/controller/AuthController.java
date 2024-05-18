package com.streamPatform.gateway.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.streamPatform.gateway.utils.annotations.PublicAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import services.AuthService;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PublicAPI
    public ResponseEntity login(@JsonProperty("user_name") String userName , @JsonProperty("password") String password){
       try {
           String token = authService.login(userName, password);
           return ResponseEntity.ok().header("Authorization" , token).build();
       }catch (Exception e){
           return ResponseEntity.badRequest().body("Invalid Password");
       }
    }
}
