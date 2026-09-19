package com.hybridPOS.auth_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridPOS.auth_service.entity.MyUser;
import com.hybridPOS.auth_service.enums.Role;


public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);

    List<MyUser> findByRole(Role role);
    Optional<MyUser> findFirstByRole(Role role);
}

