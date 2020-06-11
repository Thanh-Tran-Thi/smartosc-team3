package com.smartosc.training.controllers;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.CategoryDTO;
import com.smartosc.training.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import javax.annotation.security.PermitAll;
import javax.validation.Valid;

/**
 * client
 *
 * @author thanhttt
 * @created_at 11/06/2020 - 10:17 AM
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PermitAll
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success" , categoryService.listAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id")Long id) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success" , categoryService.getCategoryById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO categoryDTO, @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success" , categoryService.saveCategory(categoryDTO, token)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO, @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success" , categoryService.updateCategory(categoryDTO, token)), HttpStatus.OK);
    }

}
