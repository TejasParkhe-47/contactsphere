package com.ContactSphere.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ContactSphere.dao.UserDao;
import com.ContactSphere.entities.User;
import com.ContactSphere.helpers.AppConstants;
import com.ContactSphere.helpers.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userdao;

    @Override
    public void deleteUser(String id) {

        User user2 = userdao.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found "));
        userdao.delete(user2);
        
    }

    @Override
    public List<User> getAllUser() {
        
        return null;
    }

    @Override
    public Optional<User> getUserById(String id) {
        
        return userdao.findById(id);
    }

    @Override
    public boolean isUserExist(String userId) {
       
        User user2 = userdao.findById(userId).orElse(null);
        return user2!=null ? true:false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userdao.findByEmail(email).orElse(null);
        return user!=null?true:false;
    }

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRolelist(List.of(AppConstants.ROLE_USER));
        return userdao.save(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        
        User user2 =  userdao.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("user not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(
            user.isPhoneVerified()
        );

        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());


        User save =userdao.save(user2);
        return Optional.ofNullable(save);

    }

    @Override
    public User getUserByEmail(String email) {
        

        return userdao.findByEmail(email).orElse(null);
    }

    

}
