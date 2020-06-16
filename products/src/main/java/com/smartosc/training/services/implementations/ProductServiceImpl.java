package com.smartosc.training.services.implementations;

import com.smartosc.training.dto.CategoryDTO;
import com.smartosc.training.dto.ProductDTO;
import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import com.smartosc.training.exceptions.ProductDuplicateException;
import com.smartosc.training.exceptions.ProductNotFoundException;
import com.smartosc.training.repositories.CategoryRepository;
import com.smartosc.training.repositories.ProductRepository;
import com.smartosc.training.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 10:58 AM
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public List<ProductDTO> listAll() {
        List<Product> products = repository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();

        if (products == null) {
            LOGGER.error("Product Service: Can't get all. No product available");
            throw new NullPointerException("Product Service: No available product");
        }
        for (Product product : products) {
            List<Category> categories = product.getCategories();
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (Category category : categories) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                categoryDTO.setDescription(category.getDescription());
                categoryDTOS.add(categoryDTO);
            }
            ProductDTO productDTO = new ProductDTO();
            productDTO.setCategories(categoryDTOS);
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setImage(product.getImage());
            productDTO.setPrice(product.getPrice());
            productDTOS.add(productDTO);
        }
        LOGGER.info("Product Service: Get all product successfully");
        return productDTOS;
    }

    @Override
    public ProductDTO getById(Long id) {
        Optional<Product> product = repository.findById(id);
        ProductDTO productDTO = new ProductDTO();
        if (!product.isPresent()) {
            LOGGER.error("Product Service: Can't get product by id. Product id " + id + " is not exist");
            throw new NullPointerException("Product is not existed ");
        }
        List<Category> categories = product.get().getCategories();
        List<CategoryDTO> categoryProductDTOS = new ArrayList<>();
        for (Category category : categories) {
            CategoryDTO categoryProductDTO = new CategoryDTO();
            categoryProductDTO.setId(category.getId());
            categoryProductDTO.setName(category.getName());
            categoryProductDTO.setDescription(category.getDescription());
            categoryProductDTOS.add(categoryProductDTO);
        }
        productDTO.setCategories(categoryProductDTOS);
        productDTO.setId(product.get().getId());
        productDTO.setName(product.get().getName());
        productDTO.setDescription(product.get().getDescription());
        productDTO.setPrice(product.get().getPrice());
        productDTO.setImage(product.get().getImage());
        LOGGER.info("Product Service: Get product by id " + id + " successfully");
        return productDTO;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        String name = productDTO.getName();
        Optional<Product> productOptional = repository.findByName(name);
        if (productOptional.isPresent()) {
            LOGGER.error("Product Service: Can't create Product id " + productDTO.getName() + " already exist");
            throw new ProductDuplicateException("Product with name " + productDTO.getName() + " already exists");
        }
        Product product = new Product();
        List<Category> categoryList = new ArrayList<>();
        List<CategoryDTO> categoryDTOList = productDTO.getCategories();

        if (categoryDTOList != null && !categoryDTOList.isEmpty()) {
            for (CategoryDTO categoryDTO : categoryDTOList) {
                Category category = categoryRepository.findById(categoryDTO.getId()).get();
                categoryList.add(category);
            }
        }
        product.setCategories(categoryList);
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage());
        repository.save(product);
        LOGGER.info("Product Service: Create Product " + productDTO.toString() + " successfully");
        return productDTO;
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Optional<Product> product = repository.findById(productDTO.getId());
        if (product.isPresent()) {
            Product product1 = product.get();
            List<Category> categoryList = new ArrayList<>();
            for (CategoryDTO categoryDTO : productDTO.getCategories()) {
                Category category = categoryRepository.findById(categoryDTO.getId()).get();
                categoryList.add(category);
            }
            product1.setImage(productDTO.getImage());
            product1.setPrice(productDTO.getPrice());
            product1.setDescription(productDTO.getDescription());
            product1.setName(productDTO.getName());
            product1.setCategories(categoryList);
            repository.save(product1);
            LOGGER.info("Product Service: Update Product " + productDTO.toString() + " successfully");
        } else {
            LOGGER.error("Product Service: Can't update. Product id " + productDTO.getId() + " not found");
            throw new ProductNotFoundException("Not found. Product with ID - " + productDTO.getId() + "not is existed. Can't update");
        }
        return productDTO;
    }

    @Override
    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            LOGGER.info("Product Service: Delete Id " + id + " successfully");
        } else {
            LOGGER.error("Product Service: Can't delete. Id " + id + " not found");
            throw new ProductNotFoundException("Not found. Product with ID - " + id + "not is existed. Can't delete");
        }
    }
}
