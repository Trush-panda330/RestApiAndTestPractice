package com.example.rest.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.rest.domain.model.User;
import com.example.rest.domain.repository.UserRepository;
import com.example.rest.domain.service.UserServiceImpl;

public class UserServiceImplTest {

	/* Mockオブジェクト。
	 * このMockは実際のUserRepositoryの動作を模倣して
	 * テスト用に定義した動作を指定できる。
	 * 
	 * Mockオブジェクトはfinal指定しない
	 * Mockito がそのおフィールドにモックオブジェクトおを注入できなくなるため。
	 * Mockitoは内部でリフレクションを利用してモックオブジェクトをフィールドに設定するが
	 * finalのフィールドには値を変更することができず注入が失敗する。
	 * */
	@Mock
	private UserRepository userRepository;

	/* ＠InjectMocks テスト対象を指定するUserServiceのインスタンスが作成され、
	 * その内部で依存するUserRepository（モックオブジェクト)が自動的に注入される。
	 * これにより、実際のリポジトリではなくモックを使ってテストを行える*/
	@InjectMocks
	private UserServiceImpl userService;

	private User user1;
	private User user2;
	private User user3;
	private List<User> userList;

	@BeforeEach
	//テストに使うユーザーのセットアップBeforeEachにより各テストの前にセットされる
	void setUp() {
		user1 = new User(1L, "uetak", 28, "Osaka");
		user2 = new User(2L, "kaoru", 37, "Tokyo");
		user3 = new User(3L, "thina", 27, "Canada");
		
		// ユーザーオブジェクトをリストにまとめる
		userList = List.of(user1,user2, user3);

				//Mockitoのモックオブジェクトを初期化する
				MockitoAnnotations.openMocks(this);
	}

	/*ユーザーの一覧が正しく取得できるかのテスト
	 * ユーザーリポジトリが返すデータがリストとしてサービスに渡され
	 * それが適切に処理されるかを確認する。
	 * */
	@Test
	void testFindAllUser() {
		when(userRepository.findAll()).thenReturn(List.of(user1));

		List<User> result = userService.findAllUser();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("uetak", result.get(0).getName());
		assertEquals("Osaka", result.get(0).getAddress());

	}

	@Test
	void testFindByIdUser() {
		when(userRepository.findByIdUser(1L)).thenReturn(user1);

		User result = userService.findByIdUser(1L);

		assertNotNull(result);
		assertEquals("uetak", result.getName());
		assertEquals(28, result.getAge());
	}

	@Test
	void testInsertUser() {
		when(userRepository.create(user1)).thenReturn(1);

		int actual = userService.insertUser(user1);

		assertEquals(1, actual);
	}

	@Test
	void testUpdate() {
		when(userRepository.update(user1)).thenReturn(1);

		int result = userService.updateUser(user1);

		assertEquals(1, result);
	}

	@Test
	void testDeleteUser() {
		when(userRepository.deleteById(1L)).thenReturn(1);

		int result = userService.deleteUser(1L);

		assertEquals(1, result);
	}
}
