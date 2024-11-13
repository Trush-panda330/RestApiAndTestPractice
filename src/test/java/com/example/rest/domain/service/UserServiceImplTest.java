package com.example.rest.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.NoSuchElementException;

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
//		when(userRepository.findAll()).thenReturn(List.of(user1));
		when(userRepository.findAll()).thenReturn(userList);

		List<User> result = userService.findAllUser();

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals("uetak", result.get(0).getName());
		assertEquals("Osaka", result.get(0).getAddress());
		assertEquals("thina",result.get(2).getName());

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
	
	//存在しないユーザーIDを指定して例外が発生するかのテスト
	@Test
	void testFindbyIdUserWithInvalidId() {
		// anyLong()で指定された任意のlong型の引数を受け取る場合に
		//その戻り値としてnullを返すように設定している
		when(userRepository.findByIdUser(anyLong())).thenReturn(null);
		
		/*この行ではfindByIdUser(99L)を実行した際に、
		 * NosuchElementExceptionがスローされることを確認している*/
		/*もっと具体的にassertThrows()について説明すると
		 * 第一引数に発生すべき例外の方を
		 * 第二引数に実行されるコードをラムダ式書いている。*/
		assertThrows(NoSuchElementException.class, () -> userService.findByIdUser(99L));
		/* 個々のラムダ式では引数が埋まることはまあないらしいのでこういう形で使うと覚えておこう*/
		
	}
	
	//存在しないIDでの削除処理をし例外が発生するか確認
	@Test
	void testDeleteUserWithInvalidId() {
		when(userRepository.deleteById(anyLong())).thenReturn(0); //0は0件の削除つまり失敗を意味する
		
		int result = userService.deleteUser(99L);
		assertEquals(0,result);
	}
	
	
	//境界地テスト
	//空の名前や負の年齢でユーザーを作成し、挿入が失敗するか確認
	@Test
	void tesuInsertUserWithInvalidData() {
		User invalidUser = new User(4L, "", -1, "Unknow");
		
		when(userRepository.create(invalidUser)).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> userService.insertUser(invalidUser));
	}
	
	
	/*トランザクション管理のテスト
	 * トランザクション管理のテストは複数の操作を行い、
	 * 途中で例外などが発生したときに全てがロールバックされるかを確認する*/
//	@Test
//	@Transactional
//	void testTransactionalOperationRollBack() {
//	    // 最初の操作は成功、2つ目の操作で例外を発生させるようにモック
//	    when(userRepository.create(user1)).thenReturn(1);  // user1の挿入が成功するようにモック
//	    when(userRepository.create(user2)).thenThrow(RuntimeException.class);  // user2の挿入で例外を発生させる
//
//	    // 例外がスローされることを確認
//	    assertThrows(RuntimeException.class, () -> {
//	        userService.insertUser(user1);
//	        userService.insertUser(user2);  // ここで例外発生し、ロールバック
//	    });
//
//	    // ユーザー1の挿入が1回呼ばれ、ユーザー2の挿入は呼ばれないことを確認
//	    verify(userRepository, times(1)).create(user1);  // user1は1回呼ばれるべき
//	    verify(userRepository, times(0)).create(user2);  // user2は例外が発生して呼ばれないべき
//	}
	
	//これ意味不明で死んだ

}