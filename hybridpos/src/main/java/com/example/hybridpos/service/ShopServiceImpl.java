package com.example.hybridpos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.ShopCreateDTO;
import com.example.hybridpos.dto.ShopDTO;
import com.example.hybridpos.entity.Shop;
import com.example.hybridpos.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    @Override
    public List<ShopDTO> getAll() {
        List<Shop> products = shopRepository.findAll();
        return products.stream().map(this::mapToResponse).toList();
    }
    @Override
    public Shop createShop(ShopCreateDTO dto){
        Shop shop = new Shop();
        shop.setName(dto.getName());
        shop.setAddress(dto.getAddress());
        return shopRepository.save(shop);
    }
    
    private ShopDTO mapToResponse(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.setId(shop.getId());
        dto.setName(shop.getName());
        return dto;
    }
}
