package com.hybridPOS.auth_service.service;


import java.util.List;

import com.hybridPOS.auth_service.dto.RegisterCashierDTO;
import com.hybridPOS.auth_service.dto.UpdateCashierDTO;
import com.hybridPOS.auth_service.entity.MyUser;



public interface UserService {

    MyUser createCashier(RegisterCashierDTO dto);

    void deleteCashier(Long id);

    MyUser updateCashier(Long id, UpdateCashierDTO dto);

    List<MyUser> getAllCashiers();
}
