package com.example.rest.domain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.domain.exception.UserNotFoundException;
import com.example.rest.domain.model.User;
import com.example.rest.domain.response.ApiUserResponse;
import com.example.rest.domain.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserServiceImpl userService;

	/**
	 * 全ユーザー情報を取得するメソッドです。
	 * 
	 * @return ユーザー一覧のレスポンス
	 */
	@GetMapping
	public ResponseEntity<ApiUserResponse<List<User>>> findAll() {
		List<User> users = userService.findAllUser();
		ApiUserResponse<List<User>> response = new ApiUserResponse<List<User>>(users, "Users feched successfully",
				true);
		return ResponseEntity.ok(response);
	}

	/**
	 * 指定されたIDのユーザー情報を取得するエンドポイントです。
	 * 
	 * @param id ユーザーのID
	 * @return 指定されたIDのユーザー情報を含む ResponseEntity<UserResponse>
	 * @throw 指定したIDのユーザー情報がない場合のスローされる UserNotFoundException
	 */
	@GetMapping("{id}")
	public ResponseEntity<ApiUserResponse<User>> findById(@PathVariable Long id) {
		User user = userService.findByIdUser(id);
		if (user == null) {
			throw new UserNotFoundException("ID:" + id + "のユーザーは存在しません。");
		}
		ApiUserResponse<User> response = new ApiUserResponse<>(user, "User fetched successfully", true);
		return ResponseEntity.ok(response);
	}

	/**
	 * 新しいユーザーを登録するメソッドです。
	 * 
	 * @param user 新規登録するUser情報
	 * @return 新規登録が成功した場合の201のCreatedステータス
	 */
	@PostMapping
	public ResponseEntity<ApiUserResponse<?>> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// エラー時の処理
			ApiUserResponse<Object> errorResponse = new ApiUserResponse<>("error",
					bindingResult.getAllErrors().toString(), false);
			return ResponseEntity.badRequest().body(errorResponse);
		}
		userService.insertUser(user);
		// 成功時の処理
		ApiUserResponse<User> response = new ApiUserResponse<>(user, "User created successfully", true);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * ユーザーの情報を更新するメソッドです。
	 * 
	 * @param id 更新するユーザーのIＤ
	 * @param updatedUser 更新内容
	 * @return 更新後のユーザー情報
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ApiUserResponse<User>> updateUser(
			@PathVariable Long id, @Valid @RequestBody User updatedUser) {

		// リクエストボディにIDをセットする
		updatedUser.setId(id);

		int result = userService.updateUser(updatedUser);

		if (result == 0) {
			throw new UserNotFoundException("User with ID " + id + " not found");
		}

		ApiUserResponse<User> response = new ApiUserResponse<>(updatedUser, "User updated successfully", true);
		return ResponseEntity.ok(response);
	}

	/**
	 * ユーザー情報を削除するメソッドです。
	 * 
	 * @param id 削除するユーザーID
	 * @return 削除成功レスポンス
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiUserResponse<String>> deleteUser(@PathVariable Long id) {
		int result = userService.deleteUser(id);
		if (result == 0) {
			throw new UserNotFoundException("ID：" + id + "のユーザーは存在しません。");
		}
		ApiUserResponse<String> response = new ApiUserResponse<String>(null, "ユーザーの削除に成功しました。", true);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}

}
