package com.smartosc.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.training.controllers.CategoryController;
import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.CategoryProductDTO;
import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.services.CategoryService;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    void findAllFailWith404() throws Exception {
        when(categoryService.listAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/api/categories"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByIdSuccessfully() throws Exception {
        final Long id = 1L;
        when(categoryService.getById(id)).thenReturn(categoryDTOList.get(0));
        this.mockMvc.perform(get("/api/categories/{id}/products", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(categoryDTOList.get(0).getName())));
    }

    @Test
    void shouldReturn404WhenFindCategoryById () throws Exception {
        final Long id = 1L;
        when(categoryService.getById(id)).thenReturn(null);
        this.mockMvc.perform(get("/api/categories/{id}/products", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCategorySuccessfully () throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(null, "category 1", "category 1");

        when(categoryService.save(any(CategoryDTO.class))).thenReturn(categoryDTO);

        this.mockMvc.perform(
                post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name", is(categoryDTO.getName())));
    }

    @Test
    void updateCategorySuccessfully () throws Exception {
        CategoryProductDTO categoryDTO = new CategoryProductDTO(null, "category 1", "category 1");
        when(categoryService.update(any(CategoryProductDTO.class))).thenReturn(categoryDTO);

        this.mockMvc.perform(
                put("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(categoryDTO.getName())));
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
