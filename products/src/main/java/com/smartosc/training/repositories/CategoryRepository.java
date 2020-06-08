package com.smartosc.training.repositories;

import com.smartosc.training.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 4:55 PM
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
