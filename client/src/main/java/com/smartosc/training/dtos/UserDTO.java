package com.smartosc.training.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;


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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO extends AbstractDTO{
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("email")
    private String email;
}
