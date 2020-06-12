package com.smartosc.training.services.implementations;

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
 * @created_at 04/06/2020 - 4:55 PM
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<CategoryProductDTO> listAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryProductDTO> categoryDTOS = new ArrayList<>();
        List<ProductCategoryDTO> productDTOS = new ArrayList<>();
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            if (products != null) {
                for (Product product : products) {
                    ProductCategoryDTO productDTO = new ProductCategoryDTO();
                    productDTO.setId(product.getId());
                    productDTO.setName(product.getName());
                    productDTO.setDescription(product.getDescription());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setImage(product.getImage());
                    productDTOS.add(productDTO);
                }
            }
            CategoryProductDTO categoryDTO = new CategoryProductDTO();
            categoryDTO.setProducts(productDTOS);
            categoryDTO.setName(category.getName());
            categoryDTO.setId(category.getId());
            categoryDTO.setDescription(category.getDescription());
            categoryDTOS.add(categoryDTO);
        }
        LOGGER.info("Category Service: Get all category successfully");
        return categoryDTOS;
    }

    @Override
    public CategoryProductDTO getById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        CategoryProductDTO categoryDTO = new CategoryProductDTO();

        if (category.isPresent()) {
            List<Product> products = category.get().getProducts();
            List<ProductCategoryDTO> productDTOS = new ArrayList<>();
            for (Product product : products) {
                ProductCategoryDTO productDTO = new ProductCategoryDTO();
                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setDescription(product.getDescription());
                productDTO.setPrice(product.getPrice());
                productDTO.setImage(product.getImage());
                productDTOS.add(productDTO);
            }
            categoryDTO.setProducts(productDTOS);
            categoryDTO.setName(category.get().getName());
            categoryDTO.setId(category.get().getId());
            categoryDTO.setDescription(category.get().getDescription());
        } else {
            LOGGER.error("Category Service: Category is not existed");
            throw new NullPointerException("Category is not existed");
        }
        LOGGER.info("Category Service: Get category by id " + id +" successfully");
        return categoryDTO;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        String name = categoryDTO.getName();
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        if (categoryOptional.isPresent()) {
            LOGGER.error("Category Service: Can't update. Category with name " + categoryDTO.getName() + " not existed");
            throw new ProductDuplicateException("Category with name " + categoryDTO.getName() + " already exists");
        }
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
        LOGGER.info("Category Service: Create Category " + categoryDTO.toString() +" successfully");
        return categoryDTO;
    }

    @Override
    public CategoryProductDTO update(CategoryProductDTO dto) {
        Optional<Category> category = categoryRepository.findById(dto.getId());
        if (category.isPresent()) {
            Category category1 = category.get();
            List<Product> productList = new ArrayList<>();
            if (category.get().getProducts() != null) {
                for (ProductCategoryDTO productDTO : dto.getProducts()) {
                    Product product = productRepository.findById(productDTO.getId()).get();
                    productList.add(product);
                }
            } else {
                throw new NullPointerException();
            }
            category1.setName(dto.getName());
            category1.setDescription(dto.getDescription());
            category1.setProducts(productList);
            categoryRepository.save(category1);
            LOGGER.info("Category Service: Update Category " + dto.toString() +" successfully");
            return dto;
        } else {
            LOGGER.error("Category Service: Category with ID - " + dto.getId() + "not is existed. Can't update");
            throw new CategoryNotFoundException("Not found. Category with ID - " + dto.getId() + "not is existed. Can't update");
        }
    }
}
