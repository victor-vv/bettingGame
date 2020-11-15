package com.example.bettingGame.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTH_USER_ROLES", schema = "BETTINGG")
public class UserRole {

    @Id
    @SequenceGenerator(name = "AUTH_USER_ROLES_SEQUENCE", sequenceName = "BETTINGG.AUTH_USER_ROLES_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "AUTH_USER_ROLES_SEQUENCE")
    @Column(name = "USER_ROLE_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "USER_ROLE_USER_ID", updatable = false, insertable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ROLE_USER_ID")
    private User user;

    @Column(name = "USER_ROLE_ROLE_ID", updatable = false, insertable = false)
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ROLE_ROLE_ID")
    private Role role;
}
