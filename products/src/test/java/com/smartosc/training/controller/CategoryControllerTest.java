package com.smartosc.training.controller;

import com.smartosc.training.controllers.CategoryController;
import com.smartosc.training.controllers.ProductController;
import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.CategoryProductDTO;
import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import com.smartosc.training.services.CategoryService;
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
public class CategoryControllerTest {
    private static ProductDTO p1;
    private static ProductDTO p2;

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService ;

    @InjectMocks
    private CategoryController categoryController;

    private List<CategoryProductDTO> categoryDTOList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        List<CategoryDTO> categoryList = new ArrayList<>();
//        CategoryDTO category = new CategoryDTO(1L, "category 1", "category 1");
//        categoryList.add(category);

        CategoryProductDTO categoryDTO = new CategoryProductDTO(1L, "category 1", "category 1");
        CategoryProductDTO categoryDTO1 = new CategoryProductDTO(2L, "category 2", "category 2");

        categoryDTOList = new ArrayList<>();

        categoryDTOList.add(categoryDTO);
        categoryDTOList.add(categoryDTO1);

        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void findAll() throws Exception {
        when(categoryService.listAll()).thenReturn(categoryDTOList);
        this.mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(categoryDTOList.size())));
    }
}
