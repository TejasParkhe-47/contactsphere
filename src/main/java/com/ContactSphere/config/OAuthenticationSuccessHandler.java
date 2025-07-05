package com.ContactSphere.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginReactiveAuthenticationManager;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ContactSphere.dao.UserDao;
import com.ContactSphere.entities.Providers;
import com.ContactSphere.entities.User;
import com.ContactSphere.helpers.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserDao userdao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientid = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        var oauthuser = (DefaultOAuth2User) authentication.getPrincipal();

        User user = new User();

        user.setUserId(UUID.randomUUID().toString());
        user.setRolelist(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        if (authorizedClientid.equalsIgnoreCase("google")) {

            user.setEmail(oauthuser.getAttribute("email").toString());
            user.setProfilePic(oauthuser.getAttribute("picture").toString());
            user.setName(oauthuser.getAttribute("name").toString());
            user.setProviderUserId(oauthuser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("this acc created by google");
        }

        else if (authorizedClientid.equalsIgnoreCase("github")) {

            String email = oauthuser.getAttribute("email") != null ? oauthuser.getAttribute("email").toString()
                    : oauthuser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthuser.getAttribute("avatar_url").toString();
            String name = oauthuser.getAttribute("login").toString();
            String providerid = oauthuser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerid);
            user.setProvider(Providers.GITHUB);
            user.setAbout("this user is set by github");
        }

        else if (authorizedClientid.equalsIgnoreCase("linkdin")) {

        }

        else {

        }

        // DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        // System.out.println(user.getName());

        // String email = user.getAttribute("email").toString();
        // String name = user.getAttribute("name").toString();
        // String picture = user.getAttribute("picture").toString();

        // User user1 = new User();
        // user1.setName(name);
        // user1.setEmail(email);
        // user1.setProfilePic(picture);
        // user1.setPassword("password");
        // user1.setUserId(UUID.randomUUID().toString());
        // user1.setProvider(Providers.GOOGLE);
        // user1.setEnabled(true);
        // user1.setEmailVerified(true);
        // user1.setProviderUserId(user1.getName());
        // user1.setRolelist(List.of(AppConstants.ROLE_USER));
        // user1.setAbout("this acount is created by google");

        // User user2 = userdao.findByEmail(email).orElse(null);
        // if(user2==null){
        // userdao.save(user1);

        // }

        User user2 = userdao.findByEmail(user.getEmail()).orElse(null);
        if (user2 != null) {
            
            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

        }
        else {
            userdao.save(user);
            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

        }

    }
}
