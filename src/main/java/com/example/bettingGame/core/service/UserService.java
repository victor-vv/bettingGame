package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.dto.UserDto;
import com.example.bettingGame.core.repository.UserRepository;
import com.example.bettingGame.core.util.UserRole;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser == null) {
            return null;
        }
        existingUser.setPassword(encoder.encode(existingUser.getPassword()));
        return existingUser;
    }

    public void createUser(UserDto userDto) {
        User newUser = User.builder()
                            .username(userDto.getUsername())
                            .password(userDto.getPassword())
                            .accountNonExpired(true)
                            .accountNonLocked(true)
                            .credentialsNonExpired(true)
                            .enabled(true)
                            .authorities(ImmutableList.of(UserRole.USER))
                            .build();
        userRepository.save(newUser);
    }
}
