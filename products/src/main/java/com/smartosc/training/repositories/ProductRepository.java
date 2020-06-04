package com.smartosc.training.repositories;

import com.smartosc.training.entities.Category;
import com.smartosc.training.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 11:00 AM
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    @Query("select Product p JOIN FETCH p.categories WHERE p.id =:id" )
    List<Product> getProductByCategories(Category id);
}
