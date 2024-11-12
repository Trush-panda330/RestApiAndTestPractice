package com.example.rest.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.rest.domain.model.User;
import com.example.rest.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * サービスクラスの実装で、ユーザー関連の操作を提供します。
 * ユーザーの取得、挿入、更新、削除の操作を行います。
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceIF {

    private final UserRepository userRepository;

    /**
     * すべてのユーザーを取得します。
     * 
     * @return ユーザーのリスト
     */
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    /**
     * 指定されたIDのユーザーを取得します。
     * 
     * @param id ユーザーのID
     * @return ユーザー情報。存在しない場合はnullを返す
     */
    @Override
    public User findByIdUser(Long id) {
    	User user = userRepository.findByIdUser(id);
    	if(user == null) 
    		throw new NoSuchElementException("ID：" + id + "のユーザーは見つかりませんでした。");
       return user;
    }

    /**
     * 新しいユーザーを挿入します。
     * 
     * @param user 挿入するユーザー情報
     * @return 挿入操作の結果（影響を受けた行数）
     */
    @Override
    public int insertUser(User user) {
        return userRepository.create(user);
    }

    /**
     * 既存のユーザー情報を更新します。
     * 
     * @param user 更新するユーザー情報
     * @return 更新操作の結果（影響を受けた行数）
     */
    @Override
    public int updateUser(User user) {
        return userRepository.update(user);
    }

    /**
     * 指定されたIDのユーザーを削除します。
     * 
     * @param id 削除するユーザーのID
     * @return 削除操作の結果（影響を受けた行数）
     */
    @Override
    public int deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}
