package com.smartosc.training.apis;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.RoleDTO;
import com.smartosc.training.services.RoleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 05/06/2020 - 9:40 AM
 * @created_by Hieupv
 * @since 05/06/2020
 */
@RestController
@RequestMapping(value = "/api/role")
public class RoleApi {
    @Autowired
    private RoleService roleService;


    @GetMapping("/{name}")
    public ResponseEntity<Object> findUserById(@PathVariable("name") String name) throws NotFoundException {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        roleDTOList = roleService.findByUsersUserName(name);
        return new ResponseEntity<>(new APIResponse<List<RoleDTO>>(HttpStatus.OK.value(), "success", roleDTOList), HttpStatus.OK);
    }

}
