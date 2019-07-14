package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.Role;
import com.example.bettingGame.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
