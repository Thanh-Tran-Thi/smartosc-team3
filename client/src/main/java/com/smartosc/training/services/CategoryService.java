package com.smartosc.training.services;

import com.smartosc.training.dtos.CategoryDTO;

import java.util.List;

/**
 * client
 *
 * @author thanhttt
 * @created_at 11/06/2020 - 10:18 AM
 */
public interface CategoryService {
    List<CategoryDTO> listAll(String token) throws Exception;
    CategoryDTO getCategoryById(Long id, String token) throws Exception;
    CategoryDTO saveCategory(CategoryDTO category, String token) throws Exception;
    CategoryDTO updateCategory(CategoryDTO categoryDTO, String token);
}
