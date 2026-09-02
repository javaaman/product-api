package com.zestindia.product_api.repository;

import com.zestindia.product_api.entity.Item;
import com.zestindia.product_api.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByProduct(Product product);
}