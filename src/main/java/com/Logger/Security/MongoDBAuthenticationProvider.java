package com.Logger.Security;

import com.Logger.domain.model.UserInfo;
import com.Logger.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MongoDBAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            List<GrantedAuthority> authorities=new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
           // UserInfo client =  mongoTemplate.getCollection("user").findOne("{#: #}", userInfo.username, username).as(UserInfo.class);
            UserInfo user =userRepository.findByUserName(userName);
            loadedUser = new User(user.getUserName(),user.getPassword(),user.getRoles()==null?authorities:user.getRoles());
        } catch (Exception repositoryProblem) {
                throw new InternalAuthenticationServiceException("Invalid Credentials");
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }
}
