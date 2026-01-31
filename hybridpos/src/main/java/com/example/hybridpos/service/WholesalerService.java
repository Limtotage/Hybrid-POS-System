package com.example.hybridpos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hybridpos.entity.Product;
import com.example.hybridpos.entity.Shop;
import com.example.hybridpos.entity.WholesalerItem;
import com.example.hybridpos.entity.WholesalerList;
import com.example.hybridpos.repository.ProductRepository;
import com.example.hybridpos.repository.ShopRepository;
import com.example.hybridpos.repository.WholesalerListRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WholesalerService {

    private final WholesalerListRepository listRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public WholesalerList createList(Long shopId) {

        Shop shop = shopRepository.findById(shopId).orElseThrow();

        Optional<WholesalerList> lastListOpt =
                listRepository.findTopByShopOrderByCreatedAtDesc(shop);

        Map<Long, Integer> lastQuantities = new HashMap<>();

        if (lastListOpt.isPresent()) {
            for (WholesalerItem item : lastListOpt.get().getItems()) {
                lastQuantities.put(
                        item.getProduct().getId(),
                        item.getQuantity()
                );
            }
        }

        WholesalerList newList = new WholesalerList();
        newList.setShop(shop);
        newList.setCreatedAt(LocalDateTime.now());

        List<WholesalerItem> items = new ArrayList<>();

        for (Product product : productRepository.findByShop(shop)) {

            int previous = lastQuantities.getOrDefault(product.getId(), 0);
            int diff = product.getStockQuantity() - previous;

            if (diff > 0) {
                WholesalerItem item = new WholesalerItem();
                item.setProduct(product);
                item.setQuantity(diff);
                item.setList(newList);
                items.add(item);
            }
        }

        newList.setItems(items);
        return listRepository.save(newList);
    }
}

