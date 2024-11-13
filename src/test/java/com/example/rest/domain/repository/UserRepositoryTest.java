package com.example.rest.domain.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.rest.domain.mapper.UserMapper;
import com.example.rest.domain.model.User;

/**
 * UserRepositoryTestクラス
 * UserRepositoryのメソッドが期待通りに動作するかをテストするクラス。
 * もっくしたUserMapperを使用してデータベースとのやり取りをシミュレートする。
 */
public class UserRepositoryTest {
	
	@Mock
	//UserMapper userMapperをモックオブジェクトに
	private UserMapper userMapper;
	
	//
	private UserRepository userRepository;
	private User testUser;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		//テスト対象のUserRepositoryのインスタンスを作成
		//userRepositoryはモックオブジェクトuserMapperをコンストラクタに渡し、初期化される。
		/* この記述は 変数宣言 private UserRepository userRepositoryに
		 * アノテーション＠InjectMocks を付けることで省略ができる。 が、そうすればいいというものでもない。
		 * 詳しくはNotionのノートに。
		 * */
		userRepository = new UserRepository(userMapper);
		
		//共通のテスト用Userオブジェクトの初期化
		testUser = new User(null,"uetak",28,"Osaka");
	}
	
	/**
	 * {@link UserRepository#findAll()}のテストです。
	 * モックで設定したデータが正しく取得されるかを確認します。
	 */
	@Test
	void testFindAll() {
		when(userMapper.findAll()).thenReturn(List.of(new User(1L,"uetak", 28,"Osaka")));
		
		//メソッド実行し結果を取得
		List<User> users = userRepository.findAll();
		
		//結果のリストサイズが1であること、ユーザー名が期待通りであるか確認
		assertNotNull(users);
		assertEquals(1, users.size());
		assertEquals("uetak", users.get(0).getName());
	}
	
	/**
	 * {@link UserRepository#findByIdUser(Long)}のテスト
	 * 指定IDで取得したUserが期待通りか確認する。
	 */
	@Test
	void testFindById() {
		when(userMapper.findById(1L)).thenReturn(new User(1L,"uetak",28,"Osaka"));
		
		User user = userRepository.findByIdUser(1L);
		
		//結果がnullでない
		assertNotNull(user);
		//ユーザー名が期待通りであるか
		assertEquals("uetak", user.getName());
	}
	
	/**
	 * {@link UserRepository#create(User)}のテストです。<br>
	 * 新しいUserを作成する際の処理が期待通りか確認する。
	 */
	@Test
	void testCreate() {
		/*userMapper.insert()が呼ばれた際の動作を指定している
		 * any(User.class)はUserオブジェクトに関するワイルドカードを意味する。
		 * この場合、User型の疑似のオブジェクトを受け入れるということ。*/
		when(userMapper.insert(any(User.class))).thenReturn(1);
		
		int result = userRepository.create(testUser);
		
		/*結果を検証
		 * create()の戻り値はint型で1つ分作られたということで1を期待しactualにはresultを
		 * 
		 * verify()は第一引数のuserMapperが特定のメソッド
		 * 　（ここではinsert())が1回呼ばれたことを意味する。*/
		assertEquals(1, result);
		verify(userMapper,times(1)).insert(any(User.class));
	}
	
	/**
	 * {@link UserRepository#update(User)}のテストです。<br>
	 * 既存のUser情報を更新する処理が期待通りか確認します。
	 */
	@Test
	void testUpdate() {
		/*
		 * Mockの戻り値を設定する
		 * userMapper.update()が呼ばれたとき、任意のlong型の値と
		 * User型の引数を受け取った場合にint型の 1を返す
		 */
		when(userMapper.update(anyLong(), any(User.class))).thenReturn(1);
		
		//新しいUserオブジェクトを渡す
		int result = userRepository.update(new User(1L,"kaoru", 37,"Tokyo"));
		
		assertEquals(1, result);
		/*
		 * userMapper.updateが一回呼ばれ、渡された引数が期待通りであることを検証
		 * eq(1L)は最初の引数が1Lであることの確認、
		 * any(User.class)は第二引数が任意のUserオブジェクトであることを確認する。
		 */
		verify(userMapper, times(1)).update(eq(1L),any(User.class));
	}
	
	/**
	 * {@link UserRepository#deleteById(Long)}のテストです。<br>
	 * 指定IDのUserを削除する処理が期待通りか確認する。
	 */
	@Test
	void testDeleteById() {
		when(userMapper.deleteById(1L)).thenReturn(1);
		
		int result = userRepository.deleteById(1L);
		
		assertEquals(1,result);
		verify(userMapper,times(1)).deleteById(1L);
	}

	
	/**
	 * {@link UserRepository#findByIdUser(Long)} のテストです。<br>
	 * 無効なID（存在しないID）でユーザーを検索した場合、nullが返されることを確認する。
	 */
	@Test
	void testFindByIdWithInvalidId() {
		//無効なID(99L)を指定して、findByIdUserがnullを返すことを確認
		when(userMapper.findById(99L)).thenReturn(null);
		
		//メソッド実行と結果取得
		User result = userRepository.findByIdUser(99L);
		
		//結果の検証。assertNullの第二引数はテストが失敗したときに表示されるエラー
		assertNull(result, "無効なIDの場合はnullが返ることを期待");
	}
	
	/**
	 * {@link UserRepository#create(User)}のテストです。
	 * ユーザー名が空文字のUserを挿入しようとし失敗して0が返されることを確認する。
	 */
	@Test
	void testCreateWithEmptyName() {
		//名前が空文字のUserを作成
		User invalidUser = new User(null, "", 20, "Tokyo");
		//userMapper.insertが無効なユーザーに対して0を返すようにモックを設定
		when(userMapper.insert(invalidUser)).thenReturn(0);
		
		int result = userRepository.create(invalidUser);
		//挿入が失敗し、０が変えることを期待して検証
		assertEquals(0, result,"無効なデータの場合、挿入が失敗して0が返ることを期待");
	}
	
	/**
	 * 対象：{@link UserRepository#create(User)}
	 * 
	 * テスト内容：トランザクション中に例外が発生した場合、正常にロールバックされることを検証する。
	 * userMapper.insert()が例外をスローするように設定し、create()実行時に例外が発生するか検証する。
	 * 期待結果：RuntimeExceptionがスローされ、insertメソッドが一回呼び出される。
	 */
	@Test
	void testTransactionalOperationRolback() {
		when(userMapper.insert(any(User.class))).thenThrow(RuntimeException.class);
		
		assertThrows(RuntimeException.class,() -> userRepository.create(testUser),"RuntimeExceptionがスローされることを期待する");
		/*
		 * これ必要なのか？と思ったけどinsert()メソッドを呼び出したことによって
		 * RuntimeExceptionがスローされたことが明確になるから必要らしい。
		 */
		verify(userMapper,times(1)).insert(any(User.class));
		}
	
	/**
	 * テスト対象：UserRepository{@link #testFindAll()}
	 * 
	 * テスト内容：findAll()メソッドの結果が正しい順序と内容かどうかを検証する。
	 * userMapper.findAll()が二つのユーザーオブジェクトを返すように設定し、結果リストの内容を確認する。
	 * 期待結果：リストのサイズが2で、順序通りにユーザーの名前を含んでいる。
	 */
	@Test
	void testFindAllUserOrder() {
		//モックが二つのユーザーを返すように設定
		when(userMapper.findAll()).thenReturn(List.of(
				new User(1L, "user1", 30, "Tokyo"),
				new User(2L, "user2", 25, "Osaka")
				));
		
		List<User> users = userRepository.findAll();
		
		assertEquals(2, users.size());
		assertEquals("user1", users.get(0).getName());
		assertEquals("user2", users.get(1).getName());
	}
	
}
