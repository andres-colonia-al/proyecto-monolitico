package com.acolonia.spring.service;

import com.acolonia.spring.model.User;
import com.acolonia.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetails = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario" + username + " no existe."));

        System.out.println("Contraseña en BD: " + userDetails.getPassword());

        return new org.springframework.security.core.userdetails.User(
                userDetails.getUsername(),
                userDetails.getPassword(),
                true, true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userDetails.getRole().name()))
        );
    }
}
