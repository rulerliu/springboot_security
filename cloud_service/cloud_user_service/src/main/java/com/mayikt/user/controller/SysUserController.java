package com.mayikt.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.security.bo.AdminUserDetails;
import com.mayikt.user.api.SysUserService;
import com.mayikt.user.bo.SysRoleRspBO;
import com.mayikt.user.bo.SysUserReqBO;
import com.mayikt.user.bo.SysUserRspBO;
import com.mayikt.user.model.SysUser;
import com.mayikt.vo.CommonResult;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/user")
@Slf4j
@Api(tags = "SysUserController", description = "用户管理")
public class SysUserController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam(required = false) String ipAddress,
                              @RequestParam(required = false) Integer terminalType,
                              @RequestParam(required = false) String userAgent,
                              @RequestParam(required = false) String captcha) {
        Map<String, Object> map = sysUserService.login(username, password, ipAddress,
                terminalType, userAgent);
        if (map.get("token") == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        return CommonResult.success(map);
    }

    @ApiOperation("外部用户登录接口")
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getToken(@RequestParam String account,
                                 @RequestParam String password,
                                 @RequestParam(required = false) String ipAddress,
                                 @RequestParam(required = false) Integer terminalType,
                                 @RequestParam(required = false) String userAgent,
                                 @RequestParam(required = false) String captcha) {
        Map<String, Object> map = sysUserService.getToken(account, password, ipAddress,
                terminalType, userAgent);
        if (map.get("token") == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        return CommonResult.success(map);
    }


    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        sysUserService.logout();
        return CommonResult.success(null);
    }

    @PreAuthorize("hasAuthority('system:user:add')")
    @ApiOperation("添加用户")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult add(@Validated @RequestBody SysUserReqBO sysUserReqBO,
                            BindingResult result, Authentication authentication) {
        sysUserService.checkUserDataPermission(authentication, sysUserReqBO, "update");
        CommonResult commonResult = sysUserService.addUser(sysUserReqBO);
        log.info("添加用户成功,id={}", commonResult.getData());
        return commonResult;
    }

    @PreAuthorize("hasAuthority('system:user:update')")
    @ApiOperation("修改用户")
    @RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long userId, @Validated @RequestBody SysUserReqBO sysUserReqBO,
                               BindingResult result, Authentication authentication) {
        Assert.notNull(userId, "用户ID不能为空");
        sysUserReqBO.setUserId(userId);
        sysUserService.checkUserDataPermission(authentication, sysUserReqBO, "update");
        CommonResult commonResult = sysUserService.updateUser(sysUserReqBO);
        log.info("修改用户成功,userId={}", userId);
        return commonResult;
    }

    @ApiOperation("删除用户")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable Long userId,
                               Authentication authentication) {
        Assert.notNull(userId, "用户ID不能为空");
        AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
        if (userId.equals(userDetails.getSysUser().getUserId())) {
            return CommonResult.failed("不能删除自己");
        }
        SysUserReqBO sysUserReqBO = new SysUserReqBO();
        sysUserReqBO.setUserId(userId);
        sysUserService.checkUserDataPermission(authentication, sysUserReqBO, "update");
        SysUser sysUser = sysUserService.getByUserId(userId);
        CommonResult commonResult = sysUserService.deleteUser(sysUser);
        log.info("根据id{}删除用户成功", userId);
        return commonResult;
    }

    @ApiOperation("查看用户详情")
    @PreAuthorize("hasAuthority('system:user:get')")
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    public CommonResult<SysUserRspBO> get(@PathVariable Long userId,
                                          Authentication authentication) {
        Assert.notNull(userId, "userId不能为空");
        SysUserReqBO sysUserReqBO = new SysUserReqBO();
        sysUserReqBO.setUserId(userId);
        sysUserService.checkUserDataPermission(authentication, sysUserReqBO, "query");
        SysUserRspBO sysUserRspBO = sysUserService.getUserDetailsById(userId);
        sysUserRspBO.setPassword("");
        return CommonResult.success(sysUserRspBO);
    }

    @ApiOperation("重置密码")
    @PreAuthorize("hasAuthority('system:user:resetPass')")
    @RequestMapping(value = "/resetPass/{userId}", method = RequestMethod.GET)
    public CommonResult resetPass(@PathVariable Long userId, Authentication authentication) {
        Assert.notNull(userId, "userId不能为空");
        SysUserReqBO sysUserReqBO = new SysUserReqBO();
        sysUserReqBO.setUserId(userId);
        sysUserService.checkUserDataPermission(authentication, sysUserReqBO, "update");
        CommonResult commonResult = sysUserService.resetPass(userId, "");
        return commonResult;
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/updatePass/{userId}", method = RequestMethod.GET)
    public CommonResult updatePass(@PathVariable Long userId,
                                   @RequestParam(value = "oldPassword", required = true) String oldPassword,
                                   @RequestParam(value = "newPassword", required = true) String newPassword,
                                   Authentication authentication) {
        Assert.notNull(userId, "userId不能为空");
        AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
        if (!userId.equals(userDetails.getSysUser().getUserId())) {
            return CommonResult.failed("只能修改当前登录用户的密码");
        }
        CommonResult commonResult = sysUserService.updatePass(userDetails.getSysUser(), oldPassword, newPassword);
        return commonResult;
    }

    @ApiOperation("用户校验")
    @RequestMapping(value = "/userCheck/{userId}", method = RequestMethod.GET)
    public CommonResult userCheck(@PathVariable Long userId, @RequestParam(value = "type", required = true) Integer type,
                                  @RequestParam(value = "value", required = true) String value) {
        Assert.notNull(userId, "userId不能为空");
        CommonResult commonResult = sysUserService.userCheck(userId, type, value);
        return commonResult;
    }

    @ApiOperation("查询可分配角色列表")
    @PreAuthorize("hasAuthority('system:user:listRole')")
    @RequestMapping(value = "/listRole", method = RequestMethod.GET)
    public CommonResult<List<SysRoleRspBO>> listRole(@RequestParam(required = false) Long userId) {
        return CommonResult.success(sysUserService.getRoleList(userId));
    }

    @ApiOperation("本月登录次数统计")
    @RequestMapping(value = "/statMonthLoginCount", method = RequestMethod.GET)
    public CommonResult statMonthLoginCount() {
        Integer loginCount = sysUserService.statMonthLoginCount();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginCount", loginCount);
        return CommonResult.success(jsonObject);
    }

}