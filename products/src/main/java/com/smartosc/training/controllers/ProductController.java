package com.smartosc.training.controllers;

import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.dto.ApiResponse;
import com.smartosc.training.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 10:57 AM
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAll() {
        List<ProductDTO> products = service.listAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(new Date(),"No available product",
                    HttpStatus.NO_CONTENT.toString(), products), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ApiResponse<>(new Date(),"Get all products",
                HttpStatus.OK.toString(), products), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getById(@PathVariable(name = "id") @Min(1) Long id) {
        ProductDTO product = service.getById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(new ApiResponse<>(new Date(),"Get product by ID - " + id,
                    HttpStatus.OK.toString(), product), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> createNew(@Valid @RequestBody ProductDTO product) {
        service.save(product);
        return new ResponseEntity<>(new ApiResponse<>(new Date(),"Add new product",
                HttpStatus.OK.toString(), product), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProductDTO>> update(@Valid @RequestBody ProductDTO input) {
        service.update(input);
        return new ResponseEntity<>(new ApiResponse<>(new Date(),"Update " + input.toString(),
                HttpStatus.OK.toString(), input), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable(name = "id") Long id) {
        if (service.getById(id) == null) {
            String message = "Not Find product by id - " + id;
            return new ResponseEntity<>(new ApiResponse<>(new Date(),"Delete product failure",
                    HttpStatus.NOT_FOUND.toString(), message), HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(new ApiResponse<>(new Date(),"Delete product successfully",
                HttpStatus.OK.toString(), id), HttpStatus.OK);

    }
}
