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
import com.smartosc.training.untils.AppConstants;
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

    String message = null;

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
        LOGGER.info(AppConstants.GET_ALL_CATEGORIES_SUCCESSFULLY);
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
            LOGGER.error(AppConstants.NO_CATEGORY_AVAILABLE);
            throw new NullPointerException(AppConstants.NO_CATEGORY_AVAILABLE);
        }
        message = AppConstants.GET_CATEGORY_BY_ID_MESS + id + AppConstants.SUCCESS_MESS;
        LOGGER.info(message);
        return categoryDTO;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        String name = categoryDTO.getName();
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        if (categoryOptional.isPresent()) {
            message = AppConstants.CAN_NOT_CREATE_CATEGORY.concat(categoryDTO.getName()).concat(AppConstants.EXISTS);
            LOGGER.error(message);
            throw new ProductDuplicateException(message);
        }
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
        message = AppConstants.CAN_CREATE_CATEGORY.concat(categoryDTO.toString()).concat(AppConstants.SUCCESS_MESS);
        LOGGER.info(message);
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
            message = AppConstants.CAN_UPDATE_CATEGORY.concat(dto.toString()).concat(AppConstants.SUCCESS_MESS);
            LOGGER.info(message);
            return dto;
        } else {
            message = AppConstants.CAN_NOT_UPDATE_CATEGORY + dto.getId() + AppConstants.NOT_EXIST;
            LOGGER.error(message);
            throw new CategoryNotFoundException(message);
        }
    }
}
