package com.smartosc.training.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse<T> {
	@JsonProperty("status")
	private String status;

	@JsonProperty("message")
	private String message;

	@JsonProperty("data")
	private T data;

	public APIResponse(int status, String message) {
		this.status = Integer.toString(status);
		this.message = message;
	}

	public APIResponse(int status, String message, T data) {
		this.status = Integer.toString(status);
		this.message = message;
		this.data = data;
	}
}
