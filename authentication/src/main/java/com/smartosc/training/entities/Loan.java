package com.smartosc.training.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Loan {

	private Double money;
	private int customerId;
	private String requestId;	
	
}
