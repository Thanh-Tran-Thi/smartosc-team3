package com.smartosc.training.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 04/06/2020 - 10:55 AM
 * @created_by Hieupv
 * @since 04/06/2020
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends AbstractDTO{
    private String username;
    private String password;
    private String fullname;
    private String email;
}
