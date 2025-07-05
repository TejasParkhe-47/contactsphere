package com.ContactSphere.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ContactSphere.entities.Contact;
import com.ContactSphere.entities.User;
import com.ContactSphere.forms.ContactForm;
import com.ContactSphere.helpers.AppConstants;
import com.ContactSphere.helpers.Helper;
import com.ContactSphere.helpers.Message;
import com.ContactSphere.helpers.MessageType;
import com.ContactSphere.services.ContactService;
import com.ContactSphere.services.ImageService;
import com.ContactSphere.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {


    @Autowired
    private ImageService imageService;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactForm.class);


    @Autowired
   private ContactService contactService;

   @Autowired
   private UserService userService;

    @GetMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactform = new ContactForm();
        model.addAttribute("contactForm", contactform);

        return "user/add_contact";
    }



    @PostMapping("/add")
    public String savecontact(@Valid @ModelAttribute ContactForm contactform,Authentication authentication,BindingResult result,HttpSession session) {


        if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }
        


        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact

        User user = userService.getUserByEmail(username);
        // 2 process the contact picture

        // image process

        // uplod karne ka code
        Contact contact = new Contact();
        contact.setName(contactform.getName());
        contact.setFavorite(contactform.isFavorite());
        contact.setEmail(contactform.getEmail());
        contact.setPhoneNumber(contactform.getPhoneNumber());
        contact.setAddress(contactform.getAddress());
        contact.setDescription(contactform.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactform.getLinkdInLink());
        contact.setWebsiteLink(contactform.getWebsiteLink());

        if (contactform.getContactImage() != null && !contactform.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactform.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }
        contactService.save(contact);
        System.out.println(contactform);

        // 3 set the contact picture url

        // 4 `set message to be displayed on the view

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());


        

        return "redirect:/user/contacts/add";

    }

    // view contacts


    @GetMapping()
    public String getcontacts(@RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(value = "size",defaultValue = ""+AppConstants.PAGE_SIZE)int size,@RequestParam(value = "sortBy",defaultValue = "name")String sortBy,@RequestParam(value = "direction",defaultValue = "asc") String direction,Model model,Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);


      Page<Contact> pagecontacts =   contactService.getByUser(user,page,size,sortBy,direction);

        
        

      model.addAttribute("contacts", pagecontacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/contacts";
    }


    @GetMapping("/search")
    public String search(@RequestParam("field") String field ,@RequestParam("keyword") String value, @RequestParam(value = "size" ,defaultValue = AppConstants.PAGE_SIZE+"") int size, @RequestParam(value = "page",defaultValue="0") int page,@RequestParam(value = "sortBy", defaultValue="name") String sortBy,@RequestParam(value = "direction" ,defaultValue = "asc") String direction ,Model model,Authentication authentication){
        Page<Contact> contacts = null;

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        if(field.equalsIgnoreCase("name")){
            contacts = contactService.searchByName(value,size, page, sortBy,direction,user);
        }
        else if(field.equalsIgnoreCase("email")){

            contacts = contactService.searchByEmail(value, size, page, sortBy, direction,user);

        }
        else if(field.equalsIgnoreCase("phoneNumber")){
            contacts = contactService.searchByPhoneNumber(value, size, page, sortBy, direction,user);
        }

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contacts", contacts);
        return "user/search";
    }


    @GetMapping("/delete/{id}")
    public String postMethodName(@PathVariable String id) {
        contactService.delete(id);
        return "redirect:/user/contacts";
    }




    @GetMapping("/view/{id}")
    public String updateContactFormView(@PathVariable String id,Model model){

        var contact = contactService.getById(id);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkdInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactid", id);
        return "user/update_contact_view";
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable String id,@ModelAttribute ContactForm contactForm,Model model) {
        
        var con = new Contact()
        ;
        con.setId(id);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setLinkedInLink(contactForm.getLinkdInLink());
        
        con.setPicture(contactForm.getPicture());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setWebsiteLink(contactForm.getWebsiteLink());

        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
            String filename = UUID.randomUUID().toString();

       String imageUrl =  imageService.uploadImage(contactForm.getContactImage(), filename);
       con.setCloudinaryImagePublicId(filename);
       con.setPicture(imageUrl);
       contactForm.setPicture(imageUrl);
        }


        
        var updatedcon = contactService.update(con);
        return "redirect:/user/contacts";
    }
    
    
    

    

}
