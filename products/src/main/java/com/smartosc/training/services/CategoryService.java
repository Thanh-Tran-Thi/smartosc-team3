package com.smartosc.training.services;

import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.CategoryProductDTO;

import java.util.List;
/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 4:55 PM
 */
public interface CategoryService {
    List<CategoryProductDTO> listAll();
    CategoryProductDTO getById(Long id);
    CategoryDTO save(CategoryDTO category);
    CategoryProductDTO update(CategoryProductDTO categoryProductDTO);
}
