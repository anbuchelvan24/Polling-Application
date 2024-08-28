package com.sap.polls;

import java.util.Map;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {
    @PostMapping("/register")
    public String registerurl(@RequestBody Map<String,String> data){
        Register register = new Register();
        return register.register(data);
    }

    @PostMapping("/login")
    public boolean loginurl(@RequestBody Map<String,String> data){
        HandleLogin handlelogin = new HandleLogin();
        return handlelogin.handleCredentials(data);
    }

    @GetMapping("/getcred")
    public List<String> getcred(){
        HandleLogin handleLogin1 = new HandleLogin();
        return handleLogin1.getCredentials();
    }

    @GetMapping("/logout")
    public void logoutUser(){
        LogoutUser logoutUser = new LogoutUser();
        logoutUser.logout();
    }
}