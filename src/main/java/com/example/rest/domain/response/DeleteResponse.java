package com.example.rest.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteResponse {
	private String message;   //削除メッセージ
	private boolean success;  //成功フラグ

}
