package com.example.bettingGame.core.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum UserRole implements GrantedAuthority {
    USER("USER");

    private String name;


    @Override
    public String getAuthority() {
        return name;
    }
}
