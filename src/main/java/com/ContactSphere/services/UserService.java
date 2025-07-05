package com.ContactSphere.services;

import java.util.List;
import java.util.Optional;

import com.ContactSphere.entities.User;

public interface UserService {

    public User saveUser(User user);
    public Optional<User> getUserById(String id);
    public Optional<User> updateUser(User user);
    public void deleteUser(String id);
    public boolean isUserExist(String userId);
    public boolean isUserExistByEmail(String email);

    public User getUserByEmail(String email);
    List<User> getAllUser();

}
