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
    List<ProductDTO> getAll( String token) throws Exception;
    ProductDTO getById(Long id, String token) throws Exception;
    ProductDTO save(ProductDTO product, String token) throws Exception;
    ProductDTO update(ProductDTO productDTO, String token) throws Exception;
    Boolean delete(Long id, String token) throws Exception;
}
