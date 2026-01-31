package com.example.hybridpos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.ShopDTO;
import com.example.hybridpos.entity.Shop;
import com.example.hybridpos.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopInterfaceImpl implements ShopInterface {

    private final ShopRepository shopRepository;
    @Override
    public List<ShopDTO> getAll() {
        List<Shop> products = shopRepository.getAll();
        return products.stream().map(this::mapToResponse).toList();
    }
    private ShopDTO mapToResponse(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.setId(shop.getId());
        dto.setName(shop.getName());
        return dto;
    }
}
