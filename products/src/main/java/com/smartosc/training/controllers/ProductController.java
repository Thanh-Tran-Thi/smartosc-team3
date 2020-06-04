package com.smartosc.training.controllers;

import com.smartosc.training.entities.ApiResponse;
import com.smartosc.training.entities.Product;
import com.smartosc.training.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 10:57 AM
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping
    ResponseEntity<?> getAll() {
        try {
            List<Product> products = new ArrayList<>();
            service.listAll().forEach(products::add);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new ApiResponse<>(new Date(),products), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<?> getById(@PathVariable(name = "id")Long id) {
        Optional<Product> product = service.getById(id);
        if (product.equals(null)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(new Date(),product), HttpStatus.OK);
        }
    }

    @PostMapping
    ResponseEntity<?> createNew(@Valid @RequestBody Product product){
        service.save(product);
        return new ResponseEntity<>(new ApiResponse<Product>(new Date(), product), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<?> update(@PathVariable(name = "id")Long id, @RequestBody Product input){
        Optional<Product> productData = service.getById(id);
        if (productData.isPresent()) {
            Product product = productData.get();
            product.setName(input.getName());
            product.setDescription(input.getDescription());
            product.setImage(input.getImage());
            product.setPrice(input.getPrice());
            product.setCategories(input.getCategories());
            service.save(product);
            return new ResponseEntity<>(new ApiResponse<Product>(new Date(), input), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "id")Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
