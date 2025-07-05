package com.ContactSphere.services;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ContactSphere.dao.UserDao;

@Service
public class SecurityCustomerUserDetailsService  implements UserDetailsService{

    @Autowired
    private UserDao userdao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        
        return userdao.findByEmail(username).
        orElseThrow(()->new UsernameNotFoundException("username not found"));








    }

}
