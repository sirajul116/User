package com.example.sirajul116.user.service;

import com.example.sirajul116.user.entity.UserEntity;
import com.example.sirajul116.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + name));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
        return user.getRoleAssignments().stream()
                .map(urc -> new SimpleGrantedAuthority("ROLE_" + urc.getRole().getRoleName()))
                .collect(Collectors.toList());
    }
}
