package com.ContactSphere.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ContactSphere.entities.User;
import com.ContactSphere.forms.UserForm;
import com.ContactSphere.helpers.Message;
import com.ContactSphere.helpers.MessageType;
import com.ContactSphere.services.UserService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class PageController {


    @Autowired
    private UserService userservice;


    @GetMapping("/")
    public String home1(@RequestParam String param) {
        return "redirect:home";
    }
    
    

    @GetMapping("/home")
    public String home(Model model) {

        System.out.println("hello home");
        return "home";
    }

    // about

    @GetMapping("/about")
    public String about() {

        return "about";
    }

    // services

    @GetMapping("/services")
    public String services() {

        return "services";
    }

    @GetMapping("/contact")
    public String contact(){

        return "contact";
    }


    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        UserForm userform = new UserForm();
       
        model.addAttribute("userform", userform);
        return "register";
    }

    @PostMapping("/do-register")
    public String processregister(@Valid @ModelAttribute UserForm userform,HttpSession session,BindingResult rbinding){
        System.out.println(userform);

        if(rbinding.hasErrors()){
            return "register";
        }

        User user = new User();
        user.setName(userform.getName());
        user.setEmail(userform.getEmail());
        user.setPassword(userform.getPassword());
        user.setAbout(userform.getAbout());
        user.setPhoneNumber(userform.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://t4.ftcdn.net/jpg/05/89/93/27/360_F_589932782_vQAEAZhHnq1QCGu5ikwrYaQD0Mmurm0N.jpg");
       

      User saveduser =   userservice.saveUser(user);
      System.out.println("user saved");

        Message msg = Message.builder().content("Registration successfull").type(MessageType.green).build();
        session.setAttribute("message",msg);





        return "redirect:/register";
    }

}
