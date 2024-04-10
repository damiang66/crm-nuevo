package com.posta.crm.service;

import com.posta.crm.entity.User;
import com.posta.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> o = repository.findByEmail(email);

        if(o.isPresent()){
            User us= o.get();

            List<GrantedAuthority> permisos = new ArrayList<>();
            permisos.add(new SimpleGrantedAuthority("ROLE_"+us.getRole().toString()));

            return new org.springframework.security.core.userdetails.User(us.getEmail(),us.getPassword(),true,true,true,true,permisos);
        }
        throw new UsernameNotFoundException("Usuario no reconocido");
    }



}
