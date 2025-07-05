package com.ContactSphere.forms;

import org.springframework.web.multipart.MultipartFile;

import in.ContactSphere.validators.ValidFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {


    @NotBlank(message = "name is req")
    private String name;

    @Email(message = "invalid email")
    private String email;


    @NotBlank(message = "phone number is req")
    private String phoneNumber;

    private String address;


    private String description;

    private boolean favorite;

    private String websiteLink;

    private String linkdInLink;


    //annotation create 

    
    @ValidFile(message = "invalid message")
    private MultipartFile contactImage;


    public String picture;

}
