package com.example.rest.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiUserResponse<T> {
	private String message;
	private Long userId;
	private boolean success;

}
