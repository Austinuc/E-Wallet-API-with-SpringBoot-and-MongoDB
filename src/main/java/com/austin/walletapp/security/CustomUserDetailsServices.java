package com.austin.walletapp.security;

import com.austin.walletapp.models.User;
import com.austin.walletapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServices implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User appUser =  userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found " + email));

        return new org.springframework.security.core.userdetails
                .User(appUser.getEmail(), appUser.getPassword(), Arrays.stream( appUser.getRoles()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
    }

}
