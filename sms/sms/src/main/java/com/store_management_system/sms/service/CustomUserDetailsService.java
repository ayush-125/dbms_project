package com.store_management_system.sms.service;



import com.store_management_system.sms.model.Role;
import com.store_management_system.sms.model.User;
import com.store_management_system.sms.repository.RoleRepository;
import com.store_management_system.sms.repository.UserRepository;
import com.store_management_system.sms.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found: " + user);

        Role role = roleRepository.findById(user.getRoleId());
        // List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
        System.out.println("Role found: " + role);

        // return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return new CustomUserDetails(user);
    }
}
