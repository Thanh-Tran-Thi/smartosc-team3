package com.smartosc.training.controllers;

import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.CategoryProductDTO;
import com.smartosc.training.dto.ApiResponse;
import com.smartosc.training.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 4:54 PM
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryProductDTO>>> getAll() {
        List<CategoryProductDTO> categories = new ArrayList<>();
        service.listAll().forEach(categories::add);
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ApiResponse<>(new Date(),"Get all categories",
                HttpStatus.OK.toString(), categories), HttpStatus.OK);

    }

    @GetMapping(value = "/{id}/products")
    public ResponseEntity<ApiResponse<CategoryProductDTO>> getById(@PathVariable(name = "id") Long id) {
        CategoryProductDTO category = service.getById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(new Date(),"Get a category (with its list products) by ID - " + id,
                    HttpStatus.OK.toString(), category), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createNew(@Valid @RequestBody CategoryDTO category) {
        service.save(category);
        return new ResponseEntity<>(new ApiResponse<>(new Date(),"Add new category",
                HttpStatus.OK.toString(), category), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CategoryProductDTO>> update(@RequestBody CategoryProductDTO input) {
        service.update(input);
        return new ResponseEntity<>(new ApiResponse<>(new Date(),"Update a category" + input.toString(),
                HttpStatus.OK.toString(), input), HttpStatus.OK);
    }
}
