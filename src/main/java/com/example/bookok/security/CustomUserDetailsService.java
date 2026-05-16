package com.example.bookok.security;

import com.example.bookok.model.UserEntity;
import com.example.bookok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm user trong DB của BookOk
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + username));


        var authorities = userEntity.getRoles().stream()
                .map(role -> {
                    String roleName = role.getName().startsWith("ROLE_") ? role.getName() : "ROLE_" + role.getName();
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList());

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(), // Password này PHẢI được mã hóa BCrypt trong DB
                authorities
        );
    }
}