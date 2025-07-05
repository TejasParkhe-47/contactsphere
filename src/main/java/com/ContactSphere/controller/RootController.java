package com.ContactSphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ContactSphere.entities.User;
import com.ContactSphere.helpers.Helper;
import com.ContactSphere.services.UserService;


@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userservice;


    @ModelAttribute
    public void addLoggedInUserInformation(Model model,Authentication authentication ) {

        if(authentication==null) return ;

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userservice.getUserByEmail(username); // username is our email
        if (user == null) {

            model.addAttribute("loggedInUser", null);

        } else {

            model.addAttribute("loggedInUser", user);
            System.out.println("user profile");

        }
        

    }

    

}
