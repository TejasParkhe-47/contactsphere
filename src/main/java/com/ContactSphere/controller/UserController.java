package com.ContactSphere.controller;

import java.security.Principal;
import java.util.logging.Logger;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ContactSphere.entities.User;
import com.ContactSphere.helpers.Helper;
import com.ContactSphere.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userservice;


    

    // user dashboard  

    

    @GetMapping("/dashboard")
    public String userDashboard() {
        System.out.println("user dashboead");
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile2(Authentication authentication, Model model) {
        
        System.out.println("profile is herer......................");
        return "user/profile";
    }

    @PostMapping("/profile")
    public String userProfile1(Authentication authentication, Model model) {
        
        System.out.println("profile is herer......................");
        return "user/profile";
    }


    @GetMapping("/yourprofile")
    public String yourProfile(Authentication authentication, Model model) {
        
        System.out.println("profile is herer......................");
        return "user/profile";
    }

    // user add contact

    // user vieew

}
