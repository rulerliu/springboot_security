package com.mayikt.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mayikt.user.bo.SysRoleRspBO;
import com.mayikt.user.bo.SysUserReqBO;
import com.mayikt.user.bo.SysUserRoleReqBO;
import com.mayikt.user.bo.SysUserRspBO;
import com.mayikt.user.model.SysUser;
import com.mayikt.vo.CommonResult;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    Map<String,Object> login(String userName, String password, String ipAddr,
                             Integer terminalType, String userAgentStr);

    Map<String, Object> getToken(String account, String password, String ipAddress,
                                 Integer terminalType, String userAgentStr);

    void logout();

    SysUser getByUserId(Long userId);

    CommonResult addUser(SysUserReqBO sysUserReqBO);

    CommonResult updateUser(SysUserReqBO sysUserReqBO);

    CommonResult deleteUser(SysUser sysUser);

    CommonResult resetPass(Long userId, String newPassword);

    CommonResult updatePass(SysUser sysUser, String oldPassword, String newPassword);
    CommonResult userCheck(Long userId, Integer type, String value);

    SysUserRspBO getUserDetailsById(Long userId);

    void checkUserDataPermission(Authentication authentication, SysUserReqBO sysUserReqBO, String type);

    CommonResult setUserRoleList(Long userId, List<SysUserRoleReqBO> roleReqBOS);

    List<SysUser> listUser(SysUserReqBO sysUserReqBO);

    List<SysRoleRspBO> getRoleList(Long userId);

    Integer statMonthLoginCount();

    List<SysUser> selectList(String userName);

}
