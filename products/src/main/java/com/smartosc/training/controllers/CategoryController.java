package com.smartosc.training.controllers;

import com.smartosc.training.entities.ApiResponse;
import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import com.smartosc.training.services.CategoryService;
//import com.smartosc.training.services.implementations.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
 * @created_at 04/06/2020 - 4:54 PM
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping
    ResponseEntity<?> getAll() {
        try {
            List<Category> categories = new ArrayList<>();
            service.listAll().forEach(categories::add);
            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new ApiResponse<>(new Date(), categories), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        Optional<Category> category = service.getById(id);
        if (category.equals(null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(new Date(), category), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}/products")
    ResponseEntity<?> getAllProductsByCategory(@PathVariable(name = "id")Long id) {
        try {
            return new ResponseEntity<>(new ApiResponse<>(new Date(),service.findAllProductByCategory(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    ResponseEntity<?> createNew(@Valid @RequestBody Category category) {
        service.save(category);
        return new ResponseEntity<>(new ApiResponse<>(new Date(), category), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Category input) {
        Optional<Category> categoryData = service.getById(id);
        if (categoryData.isPresent()) {
            Category category = categoryData.get();
            category.setName(input.getName());
            category.setDescription(input.getDescription());
            category.setProducts(input.getProducts());
            service.save(category);
            return new ResponseEntity<>(new ApiResponse<Category>(new Date(), input), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
