package com.example.rest.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiUserResponse<T> {
	private T data;
	private String message;
	private boolean success;

}
