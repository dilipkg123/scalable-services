package com.dilip.inventoryservice.repository;

import com.dilip.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
//    Optional<Inventory> findBySkuCode(String skuCode); not used anymore.

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
