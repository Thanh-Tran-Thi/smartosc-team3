package com.smartosc.training.repositories;

import com.smartosc.training.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 10:55 AM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
}
