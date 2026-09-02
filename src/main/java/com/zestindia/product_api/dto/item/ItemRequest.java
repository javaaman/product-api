package com.zestindia.product_api.dto.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemRequest {

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public ItemRequest() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}