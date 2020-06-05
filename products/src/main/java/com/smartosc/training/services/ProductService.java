package com.smartosc.training.services;

import com.smartosc.training.dto.ProductDTO;
import java.util.List;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 10:58 AM
 */
public interface ProductService {
    List<ProductDTO> listAll();
    ProductDTO getById(Long id);
    ProductDTO save(ProductDTO product);
    void update(ProductDTO productDTO);
    void delete(Long id);
}
