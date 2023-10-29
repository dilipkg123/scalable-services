package com.dilip.orderservice.dto;

/***
 * This is duplicated from inventroy as we cannot access it directy from inventory.
 * We need this for OrderService
 * Ealier it was boolean.class and it is required to change to InventroryResponses of array in OrderService.
 */

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
