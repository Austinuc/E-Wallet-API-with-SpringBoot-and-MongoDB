package com.austin.walletapp.utils;
import com.austin.walletapp.models.User;
import com.austin.walletapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AuthDetails {

    private final UserRepository userRepository;

    public User getAuthorizedUser(Principal principal){
        if(principal!=null) {
            final UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
            return userRepository.findByEmail(currentUser.getUsername()).orElse(null);
        }
        else{
            return  null;
        }
    }
}
