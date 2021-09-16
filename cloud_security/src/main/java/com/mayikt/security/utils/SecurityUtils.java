package com.mayikt.security.utils;

import com.mayikt.exception.ApiException;
import com.mayikt.security.bo.AdminUserDetails;
import com.mayikt.user.model.SysRole;
import com.mayikt.vo.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Security相关操作
 * 1. 执行登录认证过程，通过调用 AuthenticationManager 的 authenticate(token) 方法实现
 * 2. 将认证成功的认证信息存储到上下文，供后续访问授权的时候获取使用
 * 3. 通过JWT生成令牌并返回给客户端，后续访问和操作都需要携带此令牌
 */
public class SecurityUtils {

	/**
	 * 系统登录认证
	 * @param request
	 * @param username
	 * @param password
	 * @param authenticationManager
	 * @return
	 */
//	public static JwtAuthenticatioToken login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager) {
//		JwtAuthenticatioToken token = new JwtAuthenticatioToken(username, password);
//		token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		// 执行登录认证过程
//	    Authentication authentication = authenticationManager.authenticate(token);
//	    // 认证成功存储认证信息到上下文
//	    SecurityContextHolder.getContext().setAuthentication(authentication);
//		// 生成令牌并返回给客户端
//	    token.setToken(JwtTokenUtils.generateToken(authentication));
//		return token;
//	}

	/**
	 * 获取令牌进行认证
	 * @param request
	 */
//	public static void checkAuthentication(HttpServletRequest request) {
//		// 获取令牌并根据令牌获取登录认证信息
//		Authentication authentication = JwtTokenUtil.getAuthenticationeFromToken(request);
//		// 设置登录认证信息到上下文
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//	}

	/**
	 * 获取当前用户名
	 * @return
	 */
	public static String getUsername() {
		Authentication authentication = getAuthentication();
		return getUsername(authentication);
	}
	
	/**
	 * 获取用户名
	 * @return
	 */
	public static String getUsername(Authentication authentication) {
		String username = null;
		if(authentication != null) {
			Object principal = authentication.getPrincipal();
			if(principal != null && principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();
			}
		}
		return username;
	}
	
	/**
	 * 获取当前登录信息
	 * @return
	 */
	public static Authentication getAuthentication() {
		if(SecurityContextHolder.getContext() == null) {
			return null;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication;
	}



	/**
	 * 获取当前登录的用户
	 * @return UserDetails
	 */
	public static UserDetails getCurrentUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new ApiException(ResultCode.UNAUTHORIZED);
		}
		UserDetails userDetails=null;
		try{
			userDetails = (UserDetails) authentication.getPrincipal();
		}catch(Exception e){
			throw new AccessDeniedException(ResultCode.UNAUTHORIZED.getMessage());
		}
		return userDetails;
	}

	/**
	 * 获取系统用户名称
	 *
	 * @return 系统用户名称
	 */
	public static String getCurrentUsername() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new ApiException(ResultCode.UNAUTHORIZED);
		}
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}

	/**
	 * 获取系统用户权限级别
	 */
	public static Integer getSuperRoleType() {
		AdminUserDetails currentUser = (AdminUserDetails) SecurityUtils.getCurrentUser();
		List<SysRole> roleList = currentUser.getRoles();
		Collections.sort(roleList, new Comparator<SysRole>() {
			@Override
			public int compare(SysRole o1, SysRole o2) {
				return o1.getRoleType().compareTo(o2.getRoleType());
			}
		});
		Integer superRoleType = roleList.get(0).getRoleType();
		return superRoleType;
	}
	
}