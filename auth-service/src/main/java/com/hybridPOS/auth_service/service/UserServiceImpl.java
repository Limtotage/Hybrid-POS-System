package com.hybridPOS.auth_service.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hybridPOS.auth_service.config.JwtUtil;
import com.hybridPOS.auth_service.dto.RegisterCashierDTO;
import com.hybridPOS.auth_service.dto.UpdateAdminDTO;
import com.hybridPOS.auth_service.dto.UpdateAdminResponseDTO;
import com.hybridPOS.auth_service.dto.UpdateCashierDTO;
import com.hybridPOS.auth_service.dto.UserResponseDTO;
import com.hybridPOS.auth_service.entity.MyUser;
import com.hybridPOS.auth_service.enums.Role;
import com.hybridPOS.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDTO createCashier(RegisterCashierDTO dto) {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        MyUser user = new MyUser();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.CASHIER);
        user.setEnabled(true);

        MyUser savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
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
    public UserResponseDTO updateCashier(Long id, UpdateCashierDTO dto) {

        MyUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cashier bulunamadı"));

        if (user.getRole() != Role.CASHIER) {
            throw new RuntimeException("Bu kullanıcı cashier değil");
        }

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {

            if (!dto.getUsername().equals(user.getUsername())
                    && userRepository.findByUsername(dto.getUsername()).isPresent()) {

                throw new RuntimeException("Username already exists");
            }

            user.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        MyUser updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public UpdateAdminResponseDTO updateAdmin(UpdateAdminDTO dto) {

        MyUser user = userRepository.findFirstByRole(Role.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin bulunamadı"));

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {

            if (!dto.getUsername().equals(user.getUsername())
                    && userRepository.findByUsername(dto.getUsername()).isPresent()) {

                throw new RuntimeException("Username already exists");
            }

            user.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        MyUser updatedUser = userRepository.save(user);

        // Güncel kullanıcı bilgileriyle UserDetails oluştur
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                updatedUser.getUsername(),
                updatedUser.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + updatedUser.getRole())));

        // Yeni JWT oluştur
        String newToken = jwtUtil.generateToken(userDetails);

        UpdateAdminResponseDTO response = new UpdateAdminResponseDTO();

        response.setId(updatedUser.getId());
        response.setUsername(updatedUser.getUsername());
        response.setRole(updatedUser.getRole().name());
        response.setEnabled(updatedUser.isEnabled());
        response.setToken(newToken);

        return response;
    }

    @Override
    public List<UserResponseDTO> getAllCashiers() {

        return userRepository.findByRole(Role.CASHIER)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UserResponseDTO mapToResponse(MyUser user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());

        return dto;
    }
}