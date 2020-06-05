package com.smartosc.training.controller;

import com.smartosc.training.controllers.ProductController;
import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import com.smartosc.training.services.ProductService;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * products
 *
 * @author thanhttt
 * @created_at 05/06/2020 - 5:41 PM
 */

@SpringBootTest
//@WebMvcTest(controllers = ProductController.class)
//@ActiveProfiles("test")
public class ProductControllerTest {
    private static ProductDTO p1;
    private static ProductDTO p2;

    private MockMvc mockMvc;

    @Mock
    private ProductService productService ;

    @InjectMocks
    private ProductController productController;

    private List<ProductDTO> productList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        List<CategoryDTO> categoryList = new ArrayList<>();
        CategoryDTO category = new CategoryDTO(1L, "category 1", "category 1");
        categoryList.add(category);

        ProductDTO proOne = new ProductDTO(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryList);
        ProductDTO proTwo = new ProductDTO(2L, "product 2", "This is product 2", "product_2.ipg", new BigDecimal("2.00"), categoryList);
        ProductDTO proThree = new ProductDTO(3L, "product 3", "This is product 3", "product_3.ipg", new BigDecimal("3.00"), categoryList);

        productList = new ArrayList<>();

        productList.add(proOne);
        productList.add(proTwo);
        productList.add(proThree);

        this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .build();
    }

    @Test
    void findAll() throws Exception {
        when(productService.listAll()).thenReturn(productList);
        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(productList.size())));
    }
}
