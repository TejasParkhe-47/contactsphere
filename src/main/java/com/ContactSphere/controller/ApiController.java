package com.ContactSphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ContactSphere.entities.Contact;
import com.ContactSphere.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    public ContactService contactService;


    //get contact of user
    @GetMapping("/contacts/{contactid}")
    public Contact geContact(@PathVariable
     String contactid){

        return contactService.getById(contactid);

    }

}
