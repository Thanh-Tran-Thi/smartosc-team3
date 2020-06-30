package com.smartosc.training.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * smartosc-team3
 *
 * @author Hieupv
 * @created_at 30/06/2020 - 5:28 PM
 * @created_by Hieupv
 * @since 30/06/2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class APIError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public APIError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public void setError(String error) {
        errors = Arrays.asList(error);
    }

}
