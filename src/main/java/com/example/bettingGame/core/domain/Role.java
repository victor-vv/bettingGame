package com.example.bettingGame.core.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "AUTH_ROLES", schema = "BETTINGG")
public class Role implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "AUTH_ROLES_SEQUENCE", sequenceName = "BETTINGG.AUTH_ROLES_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "AUTH_ROLES_SEQUENCE")
    @Column(name = "ROLE_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "ROLE_NAME", nullable = false)
    private String name;

    @Column(name = "ROLE_DESC")
    private String description;

    //TODO: ask Maggie on the example of Article_shop_to_physical - should we keep the second one here or not?
    //TODO: if we want to get all users with such role for example
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "AUTH_USER_ROLES",
//            joinColumns = {@JoinColumn(name = "USER_ROLES_ROLE_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "USER_ROLES_USER_ID")}
//    )
//    private Set<Role> authorities;

    @Override
    public String getAuthority() {
        return name;
    }
}
