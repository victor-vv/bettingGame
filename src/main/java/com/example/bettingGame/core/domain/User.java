package com.example.bettingGame.core.domain;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity @ToString
@Table(name = "AUTH_USERS", schema = "BETTINGG")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "AUTH_USERS_SEQUENCE", sequenceName = "BETTINGG.AUTH_USERS_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "AUTH_USERS_SEQUENCE")
    @Column(name = "USER_ID", insertable = false, updatable = false)
    private Long id;

    @NotNull(message = "Username can not be empty")
    @Column(name = "USER_NAME", nullable = false)
    private String username;

    @NotNull(message = "Password can not be empty")
    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @Email
    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired = true;

    @Column(name = "USER_ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked = true;

    @Column(name = "USER_CREDENTIALS_NON_EXPIRED")
    private boolean credentialsNonExpired = true;

    @Column(name = "USER_ENABLED")
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "AUTH_USER_ROLES", schema = "BETTINGG",
            joinColumns = {@JoinColumn(name = "USER_ROLE_USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ROLE_ROLE_ID")}
    )
    private Set<Role> authorities;
}