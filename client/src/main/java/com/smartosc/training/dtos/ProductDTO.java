package com.smartosc.training.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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
public class ProductDTO {
    private Long id;

    private String name;

    private String description;

    private String image;

    private BigDecimal price;

    private List<CategoryDTO> categories;
}
