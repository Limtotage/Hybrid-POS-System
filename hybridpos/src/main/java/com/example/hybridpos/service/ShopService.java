package com.example.hybridpos.service;

import java.util.List;

import com.example.hybridpos.dto.ShopCreateDTO;
import com.example.hybridpos.dto.ShopDTO;
import com.example.hybridpos.entity.Shop;

public interface ShopService {
    List<ShopDTO> getAll();

    Shop createShop(ShopCreateDTO dto);
}
