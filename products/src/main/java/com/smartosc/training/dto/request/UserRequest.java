package com.smartosc.training.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartosc.training.dto.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

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


public class UserRequest extends AbstractDTO {
    @NotEmpty(message = "User name không dc để trống")
    @JsonProperty("username")
    private String username;

    @NotEmpty(message = "Password không dc để trống")
    @JsonProperty("password")
    private String password;

    @NotEmpty(message = "Confirm Password không dc để trống")
    @JsonProperty("confirmpassword")
    private String confirmpassword;

    @NotEmpty(message = "Full name không dc để trống")
    @JsonProperty("fullname")
    private String fullname;

    @NotEmpty(message = "Email không dc để trống")
    @JsonProperty("email")
    private String email;
}
