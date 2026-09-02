package com.zestindia.product_api.service;

import com.zestindia.product_api.dto.item.ItemRequest;
import com.zestindia.product_api.dto.item.ItemResponse;
import com.zestindia.product_api.entity.Item;
import com.zestindia.product_api.entity.Product;
import com.zestindia.product_api.exception.ResourceNotFoundException;
import com.zestindia.product_api.repository.ItemRepository;
import com.zestindia.product_api.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ItemService(
            ItemRepository itemRepository,
            ProductRepository productRepository) {

        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    public ItemResponse createItem(
            Long productId,
            ItemRequest request) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found with id: "
                                                + productId
                                )
                        );

        Item item = new Item();

        item.setQuantity(request.getQuantity());
        item.setProduct(product);

        Item saved =
                itemRepository.save(item);

        return mapToResponse(saved);
    }

    public List<ItemResponse> getItemsByProductId(
            Long productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found with id: "
                                                + productId
                                )
                        );

        return itemRepository
                .findByProduct(product)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ItemResponse mapToResponse(Item item) {

        return new ItemResponse(
                item.getId(),
                item.getQuantity()
        );
    }
}