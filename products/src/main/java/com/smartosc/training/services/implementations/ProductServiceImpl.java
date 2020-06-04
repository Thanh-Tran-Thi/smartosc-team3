package com.smartosc.training.services.implementations;

import com.smartosc.training.entities.Product;
import com.smartosc.training.repositories.ProductRepository;
import com.smartosc.training.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 10:58 AM
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;


    @Override
    public List<Product> listAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
