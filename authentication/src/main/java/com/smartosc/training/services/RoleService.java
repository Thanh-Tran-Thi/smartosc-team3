package com.smartosc.training.services;

import com.smartosc.training.dtos.RoleDTO;

import java.util.List;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 2:29 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */

public interface RoleService {
    public RoleDTO findByName(String username);
    List<RoleDTO> findByUsersUserName(String userName);
}
