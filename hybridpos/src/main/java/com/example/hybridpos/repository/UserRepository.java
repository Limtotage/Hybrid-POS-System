package com.example.hybridpos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hybridpos.entity.MyUser;
import com.example.hybridpos.enums.Role;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);
    boolean existsByRole(Role role);
}
