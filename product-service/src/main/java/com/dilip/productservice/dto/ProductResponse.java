package com.dilip.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * it is always to have a seperate DTO obejct than the model class as I don't want to expose everthing.
 * for instance, if I add any new field tomorrow in model.product, I should have a way to limit my exposure (column level control)
 * and that can be achieved with DTO
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
