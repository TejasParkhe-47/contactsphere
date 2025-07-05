package com.ContactSphere.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ContactSphere.dao.ContactDao;
import com.ContactSphere.entities.Contact;
import com.ContactSphere.entities.User;
import com.ContactSphere.helpers.ResourceNotFoundException;


@Service
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactDao contactDao;

    @Override
    public Contact save(Contact contact) {
        
        String contactid = UUID.randomUUID().toString();
        contact.setId(contactid);

        return contactDao.save(contact);


    }

    @Override
    public Contact update(Contact contact) {
        
       var contactOld =  contactDao.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("contact not found"));
       contactOld.setName(contact.getName());
       contactOld.setAddress(contact.getAddress());
       contactOld.setDescription(contact.getDescription());
       contactOld.setEmail(contact.getEmail());
       contactOld.setFavorite(contact.isFavorite());
       contactOld.setLinkedInLink(contact.getLinkedInLink());
       contactOld.setPhoneNumber(contact.getPhoneNumber());
       contactOld.setPicture(contact.getPicture());
       contactOld.setWebsiteLink(contact.getWebsiteLink());
       contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
       

        return contactDao.save(contactOld);
    }

    @Override
    public List<Contact> getAll() {
        return contactDao.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactDao.findById(id).orElseThrow(()->new ResourceNotFoundException("contact not found"));
    }

    @Override
    public void delete(String id) {
        var contact = contactDao.findById(id).orElseThrow(()->new ResourceNotFoundException("contact not found"));


        contactDao.delete(contact);
    }

    

    @Override
    public List<Contact> getByUserId(String userid) {

       return contactDao.findByUserId(userid);
       
    }

    @Override
    public Page<Contact> getByUser(User user,int page ,int size,String sortBy,String direction) {
        Sort sort = direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        
        return contactDao.findByUser(user,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order,User user) {

       
        Sort sort = order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        return contactDao.findByUserAndEmailContaining(user,email,pageable);
    }

    @Override
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String order,User user) {
        Sort sort = order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        return contactDao.findByUserAndNameContaining(user,name, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order,User user) {
        Sort sort = order.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        return contactDao.findByUserAndPhoneNumberContaining(user,phoneNumber, pageable);
    }

    
    



}
