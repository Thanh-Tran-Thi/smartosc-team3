package com.smartosc.training.services;

import com.smartosc.training.entities.Product;

import java.util.List;
import java.util.Optional;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 10:58 AM
 */
public interface ProductService {
    List<Product> listAll();
    Optional<Product> getById(Long id);
    Product save(Product product);
    void delete(Long id);
}
