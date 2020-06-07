package com.smartosc.training.services.impls;

import com.smartosc.training.dtos.RoleDTO;
import com.smartosc.training.dtos.UserDTO;
import com.smartosc.training.services.RoleService;
import com.smartosc.training.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private RoleService roleRepository;

    @Autowired
    private UserService userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserDTO users = this.userRepository.findUserByUserName(userName);

        if (users == null || users.getStatus() == 0) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        List<GrantedAuthority> grantList = new ArrayList<>();
        List<RoleDTO> roleNames = this.roleRepository.findByUsersUserName(userName);
        if (roleNames != null) {
            for (RoleDTO role : roleNames) {
                grantList.add(new SimpleGrantedAuthority(role.getName()));
            }
        }

        return new User(users.getUsername(), users.getPassword(), grantList);
    }
}