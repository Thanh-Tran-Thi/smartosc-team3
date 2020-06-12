package com.smartosc.training.aop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiLog {
	@Id
    @GeneratedValue
    private Long id;
    private Date calledTime;
    @Column(columnDefinition = "TEXT")
    private String data;
    @Column(columnDefinition = "TEXT")
    private String errorMessage;
    private int retryNum;
}
