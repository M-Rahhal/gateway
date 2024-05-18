package com.streamPatform.gateway.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamPatform.gateway.utils.annotations.services.CryptoService;
import com.streamPatform.gateway.entity.Admin;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Data
public class AuthService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CryptoService cryptoService;

    @Value("${app.jwt.secret}")
    private String tokenSecret;
    // one week
    private long tokenExpirationTime = 1000L * 60L * 60L * 24L * 7L;


    public String login(String userName, String password) throws Exception {
        Admin admin = adminService.getAdmin(userName);
        if (admin == null) throw new NoSuchElementException("User not found");
        if (cryptoService.Hash(password).equals(admin.getPassword())) {
            ObjectMapper objectMapper = new ObjectMapper();
            return signToken(objectMapper.writeValueAsString(admin));
        } else {
            throw new Exception("Invalid password");
        }
    }


    public String signToken(String payload) {
        Algorithm algorithm = Algorithm.HMAC512(tokenSecret);
        return JWT.create()
                .withIssuer("gateway")
                .withSubject("Admin Details")
                .withClaim("payload", payload)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .withJWTId(UUID.randomUUID()
                        .toString())
                .sign(algorithm);
    }

    public Admin verifyToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC512(tokenSecret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("gateway")
                .build();
        String tokenPayload = verifier.verify(token).getClaim("payload").asString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode payloadTree = objectMapper.readTree(tokenPayload);
        Admin user = adminService.getAdmin(payloadTree.get("userName").asText());
        if (user == null)
            throw new NoSuchElementException("User not found");
        return user;
    }


    private String generateRandomToken() {
        String[] randoms = {UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()};
        return String.join("-", randoms);
    }


}