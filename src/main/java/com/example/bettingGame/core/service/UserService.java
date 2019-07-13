package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.dto.UserDto;
import com.example.bettingGame.core.repository.UserRepository;
import lombok.NonNull;
import org.hibernate.Hibernate;
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
//        Hibernate.initialize(userRepository.findByUsername(username));
        User existingUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        existingUser.setPassword(encoder.encode(existingUser.getPassword()));
        return existingUser;
    }

    /**
     *
     * @param userDto
     */
    public void createUser(UserDto userDto) {
        User newUser = User.builder()
                            .username(userDto.getUsername())
                            .password(userDto.getPassword())
                            .accountNonExpired(true)
                            .accountNonLocked(true)
                            .credentialsNonExpired(true)
                            .enabled(true)
//                            .authorities(ImmutableList.of(UserRole.USER))
                            .build();
        userRepository.save(newUser);
    }
}
