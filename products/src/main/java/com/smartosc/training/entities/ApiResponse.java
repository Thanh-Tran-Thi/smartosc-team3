package com.smartosc.training.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * products
 *
 * @author thanhttt
 * @created_at 04/06/2020 - 3:01 PM
 */
@Setter
@Getter
public class ApiResponse<T> {
    private Date calledTime;
    private T data;

    public ApiResponse() {}

    public ApiResponse(Date calledTime, T data) {
        this.calledTime = calledTime;
        this.data = data;
    }

}
