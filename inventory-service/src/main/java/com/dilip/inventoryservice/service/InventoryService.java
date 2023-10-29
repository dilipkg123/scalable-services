package com.dilip.inventoryservice.service;

import com.dilip.inventoryservice.dto.InventoryResponse;
import com.dilip.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository; // this is injected so that we can query

/*    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }*/

    @Transactional(readOnly = true)
//    @SneakyThrows // don't use in production code. this is just for assignment.
    public List<InventoryResponse> isInStock(List<String> skuCode){
/*
        Added the below block to test the retry mechanism by purposely putting the calls on-hold. Commented it as it is not needed.
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait ended");*/
     return inventoryRepository.findBySkuCodeIn(skuCode)
             .stream()
             .map(inventory ->
                 InventoryResponse.builder()
                         .skuCode(inventory.getSkuCode())
                         .isInStock(inventory.getQuantity() > 0)
                         .build()
             ).toList();
   }
}
