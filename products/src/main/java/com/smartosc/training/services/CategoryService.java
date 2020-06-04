package com.smartosc.training.services;

import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;

import java.util.List;
import java.util.Optional;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 4:55 PM
 */
public interface CategoryService {
    List<Category> listAll();
    Optional<Category> getById(Long id);
    Category save(Category category);
    List<Product> findAllProductByCategory(Long id);
}
