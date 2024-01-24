package com.amoncardim.paymentsystem.controllers;

import com.amoncardim.paymentsystem.dtos.AuthenticationRequest;
import com.amoncardim.paymentsystem.dtos.AuthenticationResponse;
import com.amoncardim.paymentsystem.dtos.UserRequest;
import com.amoncardim.paymentsystem.dtos.UserResponse;
import com.amoncardim.paymentsystem.entities.User;
import com.amoncardim.paymentsystem.services.TokenService;
import com.amoncardim.paymentsystem.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws MessagingException, UnsupportedEncodingException {
        User user = userRequest.toModel();
        UserResponse usersaved = userService.registerUser(user);
        return ResponseEntity.ok().body(usersaved);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/test")
    public String test() {
        return "você está logado";
    }
}
