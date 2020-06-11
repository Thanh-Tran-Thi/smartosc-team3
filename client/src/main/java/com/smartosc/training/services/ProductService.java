package com.smartosc.training.services;

import com.smartosc.training.dtos.ProductDTO;

import java.util.List;

/**
 * client
 *
 * @author thanhttt
 * @created_at 10/06/2020 - 1:37 PM
 */
public interface ProductService {
    List<ProductDTO> getAll();
    ProductDTO getById(Long id);
    ProductDTO save(ProductDTO product);
    ProductDTO update(ProductDTO productDTO);
    void delete(Long id);
}