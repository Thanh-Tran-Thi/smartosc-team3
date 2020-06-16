package com.smartosc.training.service;

import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.CategoryProductDTO;
import com.smartosc.training.dto.ProductCategoryDTO;
import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import com.smartosc.training.exceptions.CategoryNotFoundException;
import com.smartosc.training.exceptions.ProductDuplicateException;
import com.smartosc.training.repositories.CategoryRepository;
import com.smartosc.training.repositories.ProductRepository;
import com.smartosc.training.services.CategoryService;
import com.smartosc.training.services.ProductService;
import com.smartosc.training.services.implementations.CategoryServiceImpl;
import com.smartosc.training.services.implementations.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    private List<Category>  categoryList = new ArrayList<>();

    private Optional<Category> categoryOptional;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);

        List<Product> productList = new ArrayList<>();
        Product proOne = new Product(1L, "product 1", "This is product 1", "product_1.jpg", new BigDecimal("1.00"));
        productList.add(proOne);

        Category category1 = new Category(1L, "category 1", "category 1");
        Category category2 = new Category(2L, "category 2", "category 2", productList);
        Category category3 = new Category(3L, "category 3", "category 3", productList);

        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);

        categoryOptional = Optional.of(new Category(1L, "category 1", "category 1", productList));
    }

    @Test
    public void getAllCategoryTestSuccessfully() {
        when(categoryRepository.findAll()).thenReturn(categoryList);
        //test
        List<CategoryProductDTO> categoryProductDTOList = categoryService.listAll();

        assertEquals(categoryList.size(), categoryProductDTOList.size());
    }

    @Test
    public void getAllCategoryTestFailed() {
        when(categoryRepository.findAll()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class,()->{
            categoryService.listAll();
        });
    }

    @Test
    void shouldFetchOneCategoryByIdSuccessfully() {
        List<Product> productList = new ArrayList<>();
        Product proOne = new Product(1L, "product 1", "This is product 1", "product_1.jpg", new BigDecimal("1.00"));
        productList.add(proOne);
        Optional<Category> category = Optional.of(new Category(1L, "category 1", "category 1", productList));
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(categoryOptional);

        CategoryProductDTO categoryProductDTO = categoryService.getById(1L);
        Assertions.assertEquals(categoryProductDTO.getName(), categoryOptional.get().getName());
    }

    @Test
    void shouldFetchOneCategoryByIdFailed() {
        lenient().when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NullPointerException.class,()->{
            categoryService.getById(1L);
        });
    }

    @Test
    public void createNewCategorySuccessfully() {
        final Category category = new Category(null, "category 1", "category 1");
        final CategoryDTO categoryDTO = new CategoryDTO(null, "category 1", "category 1");

        lenient().when(categoryRepository.save(category)).thenReturn(category);
        assertThat(categoryService.save(categoryDTO)).isEqualTo(categoryDTO);
    }

    @Test
    public void createNewCategoryFailed() {
        final Category category = new Category(null, "category 1", "category 1");
        final CategoryDTO categoryDTO = new CategoryDTO(null, "category 1", "category 1");

        lenient().when(categoryRepository.findByName(categoryDTO.getName())).thenReturn(Optional.of(category));
        Assertions.assertThrows(ProductDuplicateException.class,()->{
            categoryService.save(categoryDTO);
        });
    }

    @Test
    public void updateCategorySuccessfully() {
        final Product product = new Product(1L, "product 1", "This is product 1", "product_1.jpg", new BigDecimal("1.00"), null);
        List<Product> productList = new ArrayList<>();
        Product proOne = new Product(1L, "product 1", "This is product 1", "product_1.jpg", new BigDecimal("1.00"));
        productList.add(proOne);

        List<ProductCategoryDTO> dtoList = new ArrayList<>();
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(1L, "product 1", "This is product 1", "product_1.jpg", new BigDecimal("1.00"));
        dtoList.add(productCategoryDTO);

        final Category category = new Category(1L, "category 1", "category 1", productList);

        lenient().when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        lenient().when(productRepository.findById(category.getId())).thenReturn(Optional.of(product));

        CategoryProductDTO dto = new CategoryProductDTO(1L, "category 1", "category 1", dtoList);

        final CategoryProductDTO categoryProductDTO = categoryService.update(dto);
        assertThat(categoryProductDTO).isNotNull();
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    public void updateCategoryFailed() {
        List<Product> productList = new ArrayList<>();
        Product proOne = new Product(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"));
        productList.add(proOne);

        List<ProductCategoryDTO> dtoList = new ArrayList<>();
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(1L, "product 1", "This is product 1", "product_1.jpg", new BigDecimal("1.00"));
        dtoList.add(productCategoryDTO);

        final Category category = new Category(1L, "category 1", "category 1", productList);
        CategoryProductDTO dto = new CategoryProductDTO(1L, "category 1", "category 1", dtoList);

        lenient().when(categoryRepository.findById(category.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(CategoryNotFoundException.class,()->{
            categoryService.update(dto);
        });
    }
}
