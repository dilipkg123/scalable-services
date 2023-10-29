package com.dilip.inventoryservice.controller;

import com.dilip.inventoryservice.dto.InventoryResponse;
import com.dilip.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

//    @GetMapping("/{sku-code}")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
//    public boolean isInStock(@PathVariable("sku-code") String skuCode){
//        return inventoryService.isInStock(skuCode);
//    }

    // http://localhost:8082/api/inventory?skuCode=product1&&skuCode=product2
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {  return inventoryService.isInStock(skuCode);
    }
}
