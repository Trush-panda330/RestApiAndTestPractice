package com.example.rest.domain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.rest.domain.exception.UserCreationException;
import com.example.rest.domain.exception.UserNotFoundException;
import com.example.rest.domain.response.ApiUserResponse;

/**
 * 例外処理をグローバルに管理するクラスです。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * {@link UserNotFoundException} が発生した際に呼び出されるメソッドです。
     * このメソッドでは、ユーザーが見つからなかった場合のエラーレスポンスを構築します。
     * 
     * @param userNotFoundException 発生した例外
     * @return 404 NOT_FOUND ステータスとエラーメッセージを含む ApiUserResponse
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiUserResponse<String>> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        ApiUserResponse<String> response = new ApiUserResponse<>(null, userNotFoundException.getMessage(), false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    /**
     * {@link UserCreationException} が発生した際に呼び出されるメソッドです。
     * このメソッドでは、ユーザー作成時のエラーレスポンスを構築します。
     * 
     * @param ex 発生した例外
     * @return 400 BAD_REQUEST ステータスとエラーメッセージを含む ApiUserResponse
     */
    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ApiUserResponse<String>> handleUserCreationException(UserCreationException ex) {
        ApiUserResponse<String> response = new ApiUserResponse<>(null, ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * その他の一般的な例外を処理するメソッドです。
     * このメソッドでは、サーバーエラー時のエラーレスポンスを構築します。
     * 
     * TODO これで良いのかはまだ分かりかねる（多分もっと緻密な例外処理ができると思うが一旦）
     * 
     * @param ex 発生した例外
     * @return 500 INTERNAL_SERVER_ERROR ステータスとエラーメッセージを含む ApiUserResponse
     */
    @ExceptionHandler
    public ResponseEntity<ApiUserResponse<String>> handleGeneralException(Exception ex) {
        ApiUserResponse<String> response = new ApiUserResponse<>(null, "予想外のエラーが発生しました", false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
