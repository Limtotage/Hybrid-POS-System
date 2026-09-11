package com.hybridPOS.auth_service.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hybridPOS.auth_service.dto.RegisterCashierDTO;
import com.hybridPOS.auth_service.dto.UpdateCashierDTO;
import com.hybridPOS.auth_service.entity.MyUser;
import com.hybridPOS.auth_service.enums.Role;
import com.hybridPOS.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MyUser createCashier(RegisterCashierDTO dto) {

        MyUser user = new MyUser();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.CASHIER);

        return userRepository.save(user);
    }

    @Override
    public void deleteCashier(Long id) {

        MyUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cashier bulunamadı"));

        if (user.getRole() != Role.CASHIER) {
            throw new RuntimeException("Bu kullanıcı cashier değil");
        }

        userRepository.delete(user);
    }

    @Override
    public MyUser updateCashier(Long id, UpdateCashierDTO dto) {

        MyUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cashier bulunamadı"));

        if (user.getRole() != Role.CASHIER) {
            throw new RuntimeException("Bu kullanıcı cashier değil");
        }

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public List<MyUser> getAllCashiers() {

        return userRepository.findByRole(Role.CASHIER);
    }
}