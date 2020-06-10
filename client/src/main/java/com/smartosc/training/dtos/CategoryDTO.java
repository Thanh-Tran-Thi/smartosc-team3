package com.smartosc.training.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * products
 *
 * @author thanhttt
 * @created_at 05/06/2020 - 8:52 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;

    private String name;

    private String description;

    private List<ProductDTO> products;

}
