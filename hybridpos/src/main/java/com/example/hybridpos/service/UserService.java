package com.example.hybridpos.service;

import java.util.List;

import com.example.hybridpos.dto.RegisterCashierDTO;
import com.example.hybridpos.dto.UpdateCashierDTO;
import com.example.hybridpos.entity.MyUser;

public interface UserService {

    MyUser createCashier(RegisterCashierDTO dto);

    void deleteCashier(Long id);

    MyUser updateCashier(Long id, UpdateCashierDTO dto);

    List<MyUser> getAllCashiers();
}
