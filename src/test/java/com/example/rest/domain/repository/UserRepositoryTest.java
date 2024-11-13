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

}
