package com.smartosc.training.repositories;

import com.smartosc.training.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 10:56 AM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUsersUserName(String username);
    Role findByName(String name);
}
