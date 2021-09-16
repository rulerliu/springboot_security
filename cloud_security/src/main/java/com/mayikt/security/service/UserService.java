package com.mayikt.security.service;

import com.mayikt.user.model.User;

import java.util.Set;


/**
 * 用户管理
 */
public interface UserService {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * 查找用户的菜单权限标识集合
	 * @return
	 */
	Set<String> findPermissions(String username);

}