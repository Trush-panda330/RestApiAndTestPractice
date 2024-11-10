package com.example.rest.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@NotNull(message = "IDは必須です。")
	private Long id;
	
	@NotBlank(message="名前は必須です。")
	private String name;
	
	@Min(value = 0, message="年齢は0歳以上で設定してください。")
	private Integer age;
	
	@NotBlank(message = "住所は必須です。")
	private String address;

}
