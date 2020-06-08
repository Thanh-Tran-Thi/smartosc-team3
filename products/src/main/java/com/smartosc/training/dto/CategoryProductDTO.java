package com.smartosc.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProductDTO {
    private Long id;
    private String name;
    private String description;
    private List<ProductCategoryDTO> products;

    public CategoryProductDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
