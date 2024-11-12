package com.example.rest.domain;

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
import com.example.rest.domain.repository.UserRepository;

public class UserRepositoryTest {
	
	@Mock
	//UserMapper userMapperをモックオブジェクトに
	private UserMapper userMapper;
	
	private UserRepository userRepository;
	private User testUser;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		//テスト対象のUserRepositoryのインスタンスを作成
		//userRepositoryはモックオブジェクトuserMapperをコンストラクタに渡し、初期化される。
		userRepository = new UserRepository(userMapper);
		
		//共通のテスト用Userオブジェクトの初期化
		testUser = new User(null,"uetak",28,"Osaka");
	}
	
	@Test
	void testFindAll() {
		when(userMapper.findAll()).thenReturn(List.of(new User(1L,"uetak", 28,"Osaka")));
		
		//メソッド実行
		List<User> users = userRepository.findAll();
		
		//結果を検証
		assertNotNull(users);
		assertEquals(1, users.size());
		assertEquals("uetak", users.get(0).getName());
	}
	
	@Test
	void testFindById() {
		when(userMapper.findById(1L)).thenReturn(new User(1L,"uetak",28,"Osaka"));
		
		User user = userRepository.findByIdUser(1L);
		
		assertNotNull(user);
		assertEquals("uetak", user.getName());
	}
	
	@Test
	void testCreate() {
		/*userMapper.insert()が呼ばれた際の動作を指定している
		 * any(User.class)はUserオブジェクトに関するワイルドカードを意味する。
		 * この場合、User型のに似のオブジェクトを受け入れるということ。*/
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
	
	@Test
	void testUpdate() {
		when(userMapper.update(anyLong(), any(User.class))).thenReturn(1);
		
		int result = userRepository.update(new User(1L,"kaoru", 37,"Tokyo"));
		
		assertEquals(1, result);
		verify(userMapper, times(1)).update(eq(1L),any(User.class));
	}
	
	@Test
	void testDeleteById() {
		when(userMapper.deleteById(1L)).thenReturn(1);
		
		int result = userRepository.deleteById(1L);
		
		assertEquals(1,result);
		verify(userMapper,times(1)).deleteById(1L);
	}

}
