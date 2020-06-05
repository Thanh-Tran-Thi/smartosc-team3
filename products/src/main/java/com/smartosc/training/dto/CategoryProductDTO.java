package com.smartosc.training.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * products
 *
 * @author thanhttt
 * @created_at 05/06/2020 - 9:26 AM
 */
@Setter
@Getter
public class CategoryProductDTO {
    private Long id;
    private String name;
    private String description;
    private List<ProductCategoryDTO> products;
}
