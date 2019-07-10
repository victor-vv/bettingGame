package com.example.bettingGame.core.domain;

import com.example.bettingGame.core.util.UserRole;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "CUSTOM_USERS", schema = "BETTINGG")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "USERS_SEQUENCE", sequenceName = "BETTINGG.CUSTOM_USERS_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "USERS_SEQUENCE")
    @Column(name = "USER_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "USER_NAME", nullable = false)
    private String username;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired = true;

    @Column(name = "USER_ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked = true;

    @Column(name = "USER_CREDENTIALS_NON_EXPIRED")
    private boolean credentialsNonExpired = true;

    @Column(name = "USER_ENABLED")
    private boolean enabled = true;

    @Transient
    private List<UserRole> authorities = ImmutableList.of(UserRole.USER);
}