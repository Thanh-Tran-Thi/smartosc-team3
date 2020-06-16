package com.smartosc.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.training.controllers.ProductController;
import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.entities.Product;
import com.smartosc.training.services.ProductService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

/**
 * products
 *
 * @author thanhttt
 * @created_at 05/06/2020 - 5:41 PM
 */
@SpringBootTest
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

        ProductDTO proOne = new ProductDTO(1L, "product 1", "This is product 1", "product_1.jpg", new BigDecimal("1.00"), categoryList);
        ProductDTO proTwo = new ProductDTO(2L, "product 2", "This is product 2", "product_2.jpg", new BigDecimal("2.00"), categoryList);
        ProductDTO proThree = new ProductDTO(3L, "product 3", "This is product 3", "product_3.jpg", new BigDecimal("3.00"), categoryList);

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
        this.mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(productList.size())));
    }

    @Test
    void findAllFailWith404() throws Exception {
        when(productService.listAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/api/products"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByIdSuccessfully() throws Exception {
        final Long id = 1L;
        when(productService.getById(id)).thenReturn(productList.get(0));
        this.mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(productList.get(0).getName())));
    }

    @Test()
    void shouldReturn404WhenFindProductById () throws Exception {
        final Long id = 1L;
        when(productService.getById(id)).thenReturn(null);
        this.mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProductSuccessfully () throws Exception {
        ProductDTO productDTO = new ProductDTO(null, "product 4", "This is product 4", "product_4.jpg", new BigDecimal("3.00"), null);

        when(productService.save(any(ProductDTO.class))).thenReturn(productDTO);

        this.mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.data.image", is(productDTO.getImage())));
    }

    @Test
    void updateProductSuccessfully () throws Exception {
        ProductDTO productDTO = new ProductDTO(null, "product 4", "This is product 4", "product_4.jpg", new BigDecimal("3.00"), null);
        when(productService.update(any(ProductDTO.class))).thenReturn(productDTO);

        this.mockMvc.perform(
                put("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.data.image", is(productDTO.getImage())));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Long id = 1L;
        Product product = new Product(id, "product 4", "This is product 4", "product_4.jpg", new BigDecimal("3.00"), null);
        ProductDTO productDTO = new ProductDTO(id, "product 4", "This is product 4", "product_4.jpg", new BigDecimal("3.00"), null);

        when(productService.getById(id)).thenReturn(productDTO);

        doNothing().when(productService).delete(product.getId());

        this.mockMvc.perform(delete("/api/products/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteProduct() throws Exception {
        final Long id = 1L;
        when(productService.getById(id)).thenReturn(null);
        this.mockMvc.perform(delete("/api/products/{id}", id)).andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
