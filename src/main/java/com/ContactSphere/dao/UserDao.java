package com.ContactSphere.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ContactSphere.entities.User;


@Repository
public interface UserDao extends JpaRepository<User,String> {


    public Optional<User> findByEmail(String email);
    public Optional<User> findByEmailAndPassword(String email,String password);
}
