package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.RoleDTO;
import com.smartosc.training.entities.Role;
import com.smartosc.training.repositories.RoleRepository;
import com.smartosc.training.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 2:29 PM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RoleDTO findByName(String name) {
        Role results = roleRepository.findByName(name);
        return modelMapper.map(results, RoleDTO.class);
    }

    @Override
    public List<RoleDTO> findByUsersUserName(String username) {
        List<RoleDTO> results = new ArrayList<>();
        List<Role> entities = roleRepository.findByUsersUserName(username);
        for (Role item : entities) {
            RoleDTO roleDTO = modelMapper.map(item, RoleDTO.class);
            results.add(roleDTO);
        }
        return results;
    }
}
