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
            LOGGER.error(AppConstants.NO_PRODUCT_AVAILABLE);
            throw new NullPointerException(AppConstants.NO_PRODUCT_AVAILABLE);
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
        LOGGER.info(AppConstants.GET_ALL_PRODUCTS_SUCCESSFULLY);
        return productDTOS;
    }

    @Override
    public ProductDTO getById(Long id) {
        String message = null;
        Optional<Product> product = repository.findById(id);
        ProductDTO productDTO = new ProductDTO();
        if (!product.isPresent()) {
            message = AppConstants.GET_PRODUCT_BY_ID_MESS + id + AppConstants.NOT_EXIST;
            LOGGER.error(message);
            throw new NullPointerException(message);
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
        message = AppConstants.GET_PRODUCT_BY_ID_MESS + id + AppConstants.SUCCESS_MESS;
        LOGGER.info(message);
        return productDTO;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        String name = productDTO.getName();
        Optional<Product> productOptional = repository.findByName(name);
        if (productOptional.isPresent()) {
            String message = AppConstants.CAN_NOT_CREATE.concat(productDTO.getName()).concat(AppConstants.EXISTS);
            LOGGER.error(message);
            throw new ProductDuplicateException(message);
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
        String message = AppConstants.CAN_CREATE.concat(productDTO.toString()).concat(AppConstants.SUCCESS_MESS);
        LOGGER.info(message);
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
            String message = AppConstants.CAN_UPDATE.concat(productDTO.toString()).concat(AppConstants.SUCCESS_MESS);
            LOGGER.info(message);
        } else {
            String message = AppConstants.NOT_FOUND_ID + productDTO.getId() + AppConstants.CAN_NOT_UPDATE;
            LOGGER.error(message);
            throw new ProductNotFoundException(message);
        }
        return productDTO;
    }

    @Override
    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            String message = AppConstants.CAN_DELETE + id + AppConstants.SUCCESS_MESS;
            LOGGER.info(message);
        } else {
            String message = AppConstants.CAN_NOT_DELETE + id + AppConstants.NOT_EXIST;
            LOGGER.error(message);
            throw new ProductNotFoundException(message);
        }
    }
}
