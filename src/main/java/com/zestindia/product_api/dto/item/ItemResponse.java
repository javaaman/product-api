package com.zestindia.product_api.dto.item;

public class ItemResponse {

    private Long id;
    private Integer quantity;

    public ItemResponse(
            Long id,
            Integer quantity) {

        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }
}