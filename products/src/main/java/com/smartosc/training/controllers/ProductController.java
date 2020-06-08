package com.smartosc.training.controllers;

import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.entities.ApiResponse;
import com.smartosc.training.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ProductService service;

    @GetMapping
    ResponseEntity<?> getAll() {
        try {
            List<ProductDTO> products = service.listAll();
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new ApiResponse<>(new Date(),products), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<?> getById(@PathVariable(name = "id") @Min(1) Long id) {
        ProductDTO product = service.getById(id);
        if (product.equals(null)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(new Date(),product), HttpStatus.OK);
        }
    }

    @PostMapping
    ResponseEntity<?> createNew(@Valid @RequestBody ProductDTO product){
        service.save(product);
        return new ResponseEntity<>(new ApiResponse<>(new Date(), product), HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<?> update(@RequestBody ProductDTO input){
        service.update(input);
        return new ResponseEntity<>(new ApiResponse<>(new Date(), input), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<ProductDTO> deleteById(@PathVariable(name = "id")Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
