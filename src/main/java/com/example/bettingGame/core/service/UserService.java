package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Role;
import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.repository.RoleRepository;
import com.example.bettingGame.core.repository.UserRepository;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User existingUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//        existingUser.setPassword(encoder.encode(existingUser.getPassword()));
        existingUser.setPassword("{noop}"+existingUser.getPassword());
        return existingUser;
    }

    /**
     *
     * @param user
     */
    public void createUser(User user) {

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        Role role = roleRepository.findByName("USER_ROLE").orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        user.setAuthorities(new HashSet<Role>(Collections.singletonList(role)));


//        User newUser = User.builder()
//                            .username(userDto.getUsername())
//                            .password(userDto.getPassword())
//                            .accountNonExpired(true)
//                            .accountNonLocked(true)
//                            .credentialsNonExpired(true)
//                            .enabled(true)
////                            .authorities(ImmutableList.of(UserRole.USER))
//                            .build();
        userRepository.save(user);
    }

    public void getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

    }
}
