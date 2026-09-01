package com.example.demo.controller;

import com.example.demo.jwtutil;
import lombok.RequiredArgsConstructor;
import com.example.demo.models.user;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.userrepo;
import com.example.demo.service.userservice;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class authcontroller {
    public final userrepo userrepo;
    public final userservice userservice;
    public final PasswordEncoder passwordEncoder;
    public final jwtutil jwtutil;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String,String> map){
        System.out.println("REGISTER CONTROLLER REACHED");
        String email=map.get("email");
        String password=map.get("password");
        if(userrepo.findByEmail(email).isPresent()){
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }
        userservice.createuser(user.builder().email(email).password(password).build());
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String,String> map){
        String email=map.get("email");
        String password=map.get("password");
        var useroptional = userrepo.findByEmail(email);

        if (useroptional.isEmpty()) {
            return new ResponseEntity<>("user not created", HttpStatus.NOT_FOUND);
        }

        user u = useroptional.get();

        if (!passwordEncoder.matches(password, u.getPassword())) {
            return new ResponseEntity<>("incorrect password", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtutil.genToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }

}
