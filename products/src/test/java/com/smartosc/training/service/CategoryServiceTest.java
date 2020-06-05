package com.smartosc.training.service;

import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.CategoryProductDTO;
import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import com.smartosc.training.repositories.CategoryRepository;
import com.smartosc.training.services.CategoryService;
import com.smartosc.training.services.implementations.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * products
 *
 * @author thanhttt
 * @created_at 05/06/2020 - 12:43 PM
 */
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();

    private List<Category>  categoryList = new ArrayList<>();
    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);

        List<Product> productList = new ArrayList<>();
        Product proOne = new Product(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"));
        productList.add(proOne);

        Category category1 = new Category(1L, "category 1", "category 1");
        Category category2 = new Category(2L, "category 2", "category 2", productList);
        Category category3 = new Category(3L, "category 3", "category 3", productList);

        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
    }

    @Test
    public void getAllCategoryTestSuccess()
    {
        when(categoryRepository.findAll()).thenReturn(categoryList);
        //test
        List<CategoryProductDTO> categoryProductDTOList = categoryService.listAll();

        assertEquals(categoryList.size(), categoryProductDTOList.size());
    }

}
