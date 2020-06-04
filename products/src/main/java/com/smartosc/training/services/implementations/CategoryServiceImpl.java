package com.smartosc.training.services.implementations;

import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import com.smartosc.training.repositories.CategoryRepository;
import com.smartosc.training.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 4:55 PM
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Product> findAllProductByCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id).get();

            List<Product> productList = category.getProducts();
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
