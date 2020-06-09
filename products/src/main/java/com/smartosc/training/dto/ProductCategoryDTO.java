package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * products
 *
 * @author thanhttt
 * @created_at 05/06/2020 - 8:47 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
}
