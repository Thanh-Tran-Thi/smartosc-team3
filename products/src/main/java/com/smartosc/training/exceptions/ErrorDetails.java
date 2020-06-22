package com.smartosc.training.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * products
 *
 * @author thanhttt
 * @created_at 19/06/2020 - 11:01 AM
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private Date timeCalled;
    private Integer status;
    private String message;
    private String detail;

}
