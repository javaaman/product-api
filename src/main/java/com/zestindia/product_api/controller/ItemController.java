package com.zestindia.product_api.controller;

import com.zestindia.product_api.dto.item.ItemRequest;
import com.zestindia.product_api.dto.item.ItemResponse;
import com.zestindia.product_api.service.ItemService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/{productId}/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>>
    getItemsByProductId(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                itemService.getItemsByProductId(
                        productId
                )
        );
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(
            @PathVariable Long productId,
            @Valid @RequestBody ItemRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        itemService.createItem(
                                productId,
                                request
                        )
                );
    }
}