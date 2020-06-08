package com.smartosc.training.service;

import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import com.smartosc.training.exceptions.ProductDuplicateException;
import com.smartosc.training.exceptions.ProductNotFoundException;
import com.smartosc.training.repositories.CategoryRepository;
import com.smartosc.training.repositories.ProductRepository;
import com.smartosc.training.services.ProductService;
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
import static org.mockito.Mockito.*;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 9:43 AM
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    private List<Product> productList = new ArrayList<>();

    private Optional<Product> product;

    @BeforeEach
    public void initialize() {
        MockitoAnnotations.initMocks(this);

        List<Category> categoryList = new ArrayList<>();
        Category category = new Category(1L, "category 1", "category 1", new ArrayList<>());
        categoryList.add(category);

        Product proOne = new Product(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryList);
        Product proTwo = new Product(2L, "product 2", "This is product 2", "product_2.ipg", new BigDecimal("2.00"), categoryList);
        Product proThree = new Product(3L, "product 3", "This is product 3", "product_3.ipg", new BigDecimal("3.00"), categoryList);

        productList.add(proOne);
        productList.add(proTwo);
        productList.add(proThree);

        product = Optional.of(new Product(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryList));
    }

    @Test
    public void getAllProductTest()
    {
        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDTO> productDTOList = productService.listAll();

        assertEquals(productList.size(), productDTOList.size());
    }

    @Test
    public void getAllProductTestFailed()
    {
        when(productRepository.findAll()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class,()->{
            productService.listAll();
        });
    }

    @Test
    public void findByIdSuccess(){
        LOGGER.info("fake data for function findByIdName");
        lenient().when(productRepository.findById(1L)).thenReturn(product);
        ProductDTO userResult = productService.getById(1L);
        Assertions.assertEquals(userResult.getName(), product.get().getName());
    }

    @Test
    public void findByIdFail(){
        lenient().when(productRepository.findById(1L)).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class,()->{
            productService.getById(1L);
        });
        LOGGER.error("");
    }

    @Test
    void shouldCreateProductSuccessfully() {
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category(1L, "category 1", "category 1");
        categoryList.add(category);

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO(1L, "category 1", "category 1");
        categoryDTOList.add(categoryDTO);

        final Product product = new Product( null, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryList);
        final ProductDTO productDTO = new ProductDTO(null, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryDTOList);
        lenient().when(productRepository.findByName(productDTO.getName())).thenReturn(Optional.empty());
        lenient().when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        lenient().when(productRepository.save(product)).thenReturn(product);

        ProductDTO expected = productService.save(productDTO);

        Assertions.assertEquals(expected.getName(), product.getName());
    }

    @Test
    void shouldCreateProductFailed () {
        final Product product = new Product(null, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), null);
        final ProductDTO productDTO = new ProductDTO(null, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), null);
        lenient().when(productRepository.findByName(productDTO.getName())).thenReturn(Optional.of(product));
        Assertions.assertThrows(ProductDuplicateException.class,()->{
            productService.save(productDTO);
        });
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category(1L, "category 1", "category 1", new ArrayList<>());
        categoryList.add(category);

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO(1L, "category 1", "category 1");
        categoryDTOList.add(categoryDTO);

        final ProductDTO productDTO = new ProductDTO(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryDTOList);
        final Product product = new Product(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryList);
        lenient().when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        lenient().when(categoryRepository.findById(product.getId())).thenReturn(Optional.of(category));

        final ProductDTO expected = productService.update(productDTO);
        assertThat(expected).isNotNull();
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldUpdateProductFailed() {
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category(1L, "category 1", "category 1", new ArrayList<>());
        categoryList.add(category);

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        CategoryDTO categoryDTO = new CategoryDTO(1L, "category 1", "category 1");
        categoryDTOList.add(categoryDTO);

        final ProductDTO productDTO = new ProductDTO(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryDTOList);
        final Product product = new Product(1L, "product 1", "This is product 1", "product_1.ipg", new BigDecimal("1.00"), categoryList);

        lenient().when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class,()->{
            productService.update(productDTO);
        });
    }

    @Test
    void shouldBeDeleted() throws Exception{
        final Long id = 1L;
        lenient().when(productRepository.findById(id)).thenReturn(Optional.of(new Product()));
        productService.delete(id);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldNotDeleted() throws Exception{
        final Long id = 1L;
        lenient().when(productRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductNotFoundException.class,()->{
            productService.delete(id);
        });
    }
}
