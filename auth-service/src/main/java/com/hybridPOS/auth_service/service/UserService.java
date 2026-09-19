package com.hybridPOS.auth_service.service;


import java.util.List;

import com.hybridPOS.auth_service.dto.RegisterCashierDTO;
import com.hybridPOS.auth_service.dto.UpdateAdminDTO;
import com.hybridPOS.auth_service.dto.UpdateAdminResponseDTO;
import com.hybridPOS.auth_service.dto.UpdateCashierDTO;
import com.hybridPOS.auth_service.dto.UserResponseDTO;



public interface UserService {


    UserResponseDTO createCashier(RegisterCashierDTO dto);

    void deleteCashier(Long id);

    UserResponseDTO updateCashier(Long id, UpdateCashierDTO dto);

    UpdateAdminResponseDTO updateAdmin(UpdateAdminDTO dto);

    List<UserResponseDTO> getAllCashiers();
}
