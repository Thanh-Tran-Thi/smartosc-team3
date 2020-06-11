package com.smartosc.training.apis;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.RoleDTO;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.dtos.request.UserRequest;
import com.smartosc.training.services.RoleService;
import com.smartosc.training.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
