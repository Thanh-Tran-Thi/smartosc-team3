package com.smartosc.training.controllers;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.ProductDTO;
import com.smartosc.training.services.ProductService;
import com.smartosc.training.services.impls.AuthenticateServiceImpl;
import com.smartosc.training.services.impls.RestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

/**
 * client
 *
 * @author thanhttt
 * @created_at 10/06/2020 - 11:22 AM
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @PermitAll
    public ResponseEntity<?> getAllProduct(@RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(),
                "success" ,
                productService.getAll(token)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id")Long id, @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success" , productService.getById(id, token)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDTO, @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success" , productService.save(productDTO, token)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDTO productDTO, @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success" , productService.update(productDTO, token)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id")Long id, @RequestHeader(value="Authorization") String token) {
        return new ResponseEntity<>(new APIResponse<>(HttpStatus.OK.value(), "success" , productService.delete(id, token)), HttpStatus.OK);
    }
}
