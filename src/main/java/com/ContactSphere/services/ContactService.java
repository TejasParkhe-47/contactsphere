package com.ContactSphere.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ContactSphere.entities.Contact;
import com.ContactSphere.entities.User;

public interface ContactService {


    public Contact save(Contact contact);

    public Contact update(Contact contact);

    public List<Contact> getAll();

    public Contact getById(String id);

    public void delete(String id);

    public Page<Contact> searchByEmail(String email,int size,int page,String sortBy ,String order,User user);

    public Page<Contact> searchByName(String name,int size,int page,String sortBy ,String order,User user);
    public Page<Contact> searchByPhoneNumber(String phoneNumber,int size,int page,String sortBy ,String order,User user);
    public List<Contact> getByUserId(String userId);

    public Page<Contact> getByUser(User user,int page , int size,String sortBy,String direction);

}
