package com.smartosc.training.controller;

import com.smartosc.training.controllers.ProductController;
import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.services.ProductService;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

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

    private ProductDTO ProductDTO;

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
        this.mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(productList.size())));
    }

    @Test
    void findByIdSuccessfully() throws Exception {
        final Long id = 1L;
        when(productService.getById(id)).thenReturn(productList.get(0));
        this.mockMvc.perform(get("/api/products" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("", is(productList.get(0).getName())));
    }

    @Test
    void shouldReturn404WhenFindProductById () throws Exception {
        final Long id = 1L;
        final ProductDTO productDTO = null;
        when(productService.getById(id)).thenReturn((productDTO));
        this.mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBookSuccessfully () throws Exception {
        ProductDTO productDTO = new ProductDTO(4L, "product 4", "This is product 4", "product_4.ipg", new BigDecimal("3.00"), null);

        when(productService.save(ProductDTO)).thenAnswer(invocation -> invocation.getArgument(0));

        this.mockMvc.perform(post("/api/products"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(productDTO.getName())))
                .andExpect(jsonPath("$.price", is(productDTO.getPrice())));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO(id, "product 4", "This is product 4", "product_4.ipg", new BigDecimal("3.00"), null);

        when(productService.getById(id)).thenReturn(productDTO);

        doNothing().when(productService).delete(productDTO.getId());

        this.mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(productDTO.getName())));
    }
}
