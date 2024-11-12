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
	
	private User user;
	
	@BeforeEach
	void setUp() {
		user = new User(1L,"uetak", 28, "Osaka");
		
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindAllUser() {
		when(userRepository.findAll()).thenReturn(List.of(user));
		
		List<User> result = userService.findAllUser();
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("uetak", result.get(0).getName());
		assertEquals("tokio", result.get(0).getAddress());
		
	}
	
	

}
