package com.example.hybridpos.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.AuthResponseDTO;
import com.example.hybridpos.dto.LoginDTO;
import com.example.hybridpos.dto.RegisterCashierDTO;
import com.example.hybridpos.dto.RegisterOwnerDTO;
import com.example.hybridpos.entity.MyUser;
import com.example.hybridpos.enums.Role;
import com.example.hybridpos.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //private final JwtService jwtService;

    public void registerOwner(RegisterOwnerDTO dto) {

        if (userRepository.existsByRole(Role.OWNER)) {
            throw new RuntimeException("Owner already exists");
        }

        MyUser owner = new MyUser();
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setUsername(dto.getUsername());
        owner.setPassword(passwordEncoder.encode(dto.getPassword()));
        owner.setRole(Role.OWNER);

        userRepository.save(owner);
    }

    public void registerCashier(RegisterCashierDTO dto) {

        MyUser cashier = new MyUser();
        cashier.setFirstName(dto.getFirstName());
        cashier.setLastName(dto.getLastName());
        cashier.setUsername(dto.getUsername());
        cashier.setPassword(passwordEncoder.encode(dto.getPassword()));
        cashier.setRole(Role.CASHIER);

        userRepository.save(cashier);
    }

    public AuthResponseDTO login(LoginDTO dto) {

        MyUser user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        //String token = jwtService.generateToken(user);

        AuthResponseDTO response = new AuthResponseDTO();
        //response.setToken(token);
        response.setRole(user.getRole().name());

        return response;
    }
}

