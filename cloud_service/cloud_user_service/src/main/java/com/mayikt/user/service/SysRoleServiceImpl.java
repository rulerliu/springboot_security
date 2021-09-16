package com.mayikt.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mayikt.security.bo.AdminUserDetails;
import com.mayikt.security.exception.ApiException;
import com.mayikt.security.service.UmsAdminCacheService;
import com.mayikt.security.vo.CommonResult;
import com.mayikt.security.vo.ResultCode;
import com.mayikt.user.api.SysRoleService;
import com.mayikt.user.bo.SysRoleReqBO;
import com.mayikt.user.bo.SysRoleRspBO;
import com.mayikt.user.mapper.SysMenuMapper;
import com.mayikt.user.mapper.SysRoleMapper;
import com.mayikt.user.mapper.SysRoleMenuMapper;
import com.mayikt.user.mapper.SysUserRoleMapper;
import com.mayikt.user.model.SysRole;
import com.mayikt.user.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    public static final String IS_VALID = "0";//一级角色 超级管理员
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult addRole(com.mayikt.user.bo.SysRoleReqBO sysRoleReqBO) {
//        SysRole sysRole = BeanCopyUtil.copyObject(sysRoleReqBO, SysRole.class);
//
//        //角色名称不能重复
//        SysRole dbRole = sysRoleMapper.selectByRoleName(sysRole.getRoleName());
//        if (dbRole != null) {
//            return CommonResult.failed("角色名称已存在");
//        }
//        // 新增角色
//        sysRole.setCreateTime(new Date());
//        sysRole.setStatus(IS_VALID);
//        sysRoleMapper.insert(sysRole);
//        Long roleId = sysRole.getRoleId();
//        // 新增角色功能菜单
//        List<SysRoleMenuReqBO> sysRoleMenuReqBOList = sysRoleReqBO.getRoleMenuReqBOS();
//        if (!CollectionUtils.isEmpty(sysRoleMenuReqBOList)) {
//            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuReqBOList.stream().map(menu -> new SysRoleMenu(menu.getRoleMenuId(), roleId, menu.getMenuId())).collect(Collectors.toList());
//            for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
//                sysRoleMenuMapper.insert(sysRoleMenu);
//            }
//        }
//
//        return CommonResult.success(roleId);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateRole(SysRoleReqBO sysRoleReqBO) {
//        SysRole sysRole = BeanCopyUtil.copyObject(sysRoleReqBO, SysRole.class);
//
//        SysRoleExample example = new SysRoleExample();
//        SysRoleExample.Criteria criteria = example.createCriteria();
//        criteria.andRoleNameEqualTo(sysRole.getRoleName());
//        criteria.andRoleIdNotEqualTo(sysRole.getRoleId());
//        List<SysRole> roleList = sysRoleMapper.selectByExample(example);
//
//        if (!CollectionUtils.isEmpty(roleList)) {
//            return CommonResult.failed("角色名称已存在");
//        }
//
//        // 修改角色
//        sysRoleMapper.updateById(sysRole);
//        Long roleId = sysRole.getRoleId();
//
//        // 先删除后新增功能菜单
//        SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
//        sysRoleMenuExample.createCriteria().andRoleIdEqualTo(roleId);
//        sysRoleMenuMapper.deleteByExample(sysRoleMenuExample);
//        List<SysRoleMenuReqBO> sysRoleMenuReqBOList = sysRoleReqBO.getRoleMenuReqBOS();
//        if (!CollectionUtils.isEmpty(sysRoleMenuReqBOList)) {
//            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuReqBOList.stream().map(menu -> new SysRoleMenu(menu.getRoleMenuId(), roleId, menu.getMenuId())).collect(Collectors.toList());
//            sysRoleMenuMapper.batchInsert(sysRoleMenuList);
//
//        }
//
//        AdminUserDetails currentUser = (AdminUserDetails) SecurityUtils.getCurrentUser();
//        adminCacheService.deleteRoles(currentUser.getSysUser().getUserId());

        return CommonResult.success(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteRole(Long roleId) {
//        // 删除角色功能
//        SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
//        sysRoleMenuExample.createCriteria().andRoleIdEqualTo(roleId);
//        sysRoleMenuMapper.deleteByExample(sysRoleMenuExample);
//        // 删除用户角色
//        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
//        sysUserRoleExample.createCriteria().andRoleIdEqualTo(roleId);
//        sysUserRoleMapper.deleteByExample(sysUserRoleExample);
//
//        // 删除角色
//        sysRoleMapper.deleteById(roleId);
//        AdminUserDetails currentUser = (AdminUserDetails) SecurityUtils.getCurrentUser();
//        adminCacheService.deleteRoles(currentUser.getSysUser().getUserId());
        return CommonResult.success(null);
    }

//    @Override
//    public CommonPage list(Integer pageNum, Integer pageSize, SysRoleReqBO sysRoleReqBO, Authentication authentication) {
//        return null;
//        // 查询当前角色及下级所有角色列表
//        SysRoleExample example = new SysRoleExample();
//        SysRoleExample.Criteria criteria = example.createCriteria();
//        if (!StringUtils.isEmpty(sysRoleReqBO.getRoleName())) {
//            criteria.andRoleNameLike("%" + sysRoleReqBO.getRoleName() + "%");
//        }
//
//        AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
//
//        // 当前用户  当前用户的角色
//        SysUser sysUser = userDetails.getSysUser();
//        List<SysRole> roleList = sysRoleMapper.getRoles(sysUser.getUserId());
//
//        if (CollectionUtils.isEmpty(roleList)) {//如果当前用户没有角色就没有权限查询
//            throw new ApiException(ResultCode.FORBIDDEN);
//        }
//
//        Collections.sort(roleList, new Comparator<SysRole>() {
//            @Override
//            public int compare(SysRole o1, SysRole o2) {
//                return o1.getRoleType().compareTo(o2.getRoleType());
//            }
//        });
////        SysUser currSysUser = userDetails.getSysUser();
//        // 当前用户角色类型
//        Integer superRoleType = roleList.get(0).getRoleType();
//        // 级别
//        criteria.andRoleTypeGreaterThanOrEqualTo(superRoleType);
//        if (sysRoleReqBO.getRoleType() != null) {
//            criteria.andRoleTypeEqualTo(sysRoleReqBO.getRoleType());
//        }
//        PageHelper.startPage(pageNum, pageSize);
//        List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);// 查看等级同级或低于当前用户角色的角色列表
//        List<SysRoleRspBO> sysRoleRspBOS = BeanCopyUtil.copyList(sysRoles, SysRoleRspBO.class);
//        if (!CollectionUtils.isEmpty(sysRoleRspBOS)) {
//            for (SysRoleRspBO sysRoleRspBO : sysRoleRspBOS) {
//                // 查询用户
//                SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
//                SysUserRoleExample.Criteria userRoleExampleCriteria = sysUserRoleExample.createCriteria();
//                userRoleExampleCriteria.andRoleIdEqualTo(sysRoleRspBO.getRoleId());
//                List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
//                // 查询功能菜单
//                SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
//                SysRoleMenuExample.Criteria roleMenuExampleCriteria = sysRoleMenuExample.createCriteria();
//                roleMenuExampleCriteria.andRoleIdEqualTo(sysRoleRspBO.getRoleId());
//                List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectByExample(sysRoleMenuExample);
//                List<SysUserRoleRspBO> sysUserRoleRspBOS = BeanCopyUtil.copyList(sysUserRoles, SysUserRoleRspBO.class);
//                List<SysRoleMenuRspBO> sysRoleMenuRspBOS = BeanCopyUtil.copyList(sysRoleMenuList, SysRoleMenuRspBO.class);
//                sysRoleRspBO.setSysUserRoleRspBOList(sysUserRoleRspBOS);
//                sysRoleRspBO.setSysRoleMenuRspBOList(sysRoleMenuRspBOS);
//            }
//        }
//
//
//        CommonPage page = CommonPage.restPage(sysRoles);
//        page.setList(sysRoleRspBOS);
//        return page;
//    }

    @Override
    public SysRoleRspBO getByRoleId(Long roleId) {
        return null;
//        // 查询角色
//        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
//        // 查询用户
//        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
//        SysUserRoleExample.Criteria criteria = sysUserRoleExample.createCriteria();
//        criteria.andRoleIdEqualTo(roleId);
//        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
//        // 查询功能菜单
//        SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
//        SysRoleMenuExample.Criteria criteria1 = sysRoleMenuExample.createCriteria();
//        criteria1.andRoleIdEqualTo(roleId);
//        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectByExample(sysRoleMenuExample);
//
//        SysRoleRspBO roleRspBO = BeanCopyUtil.copyObject(sysRole, SysRoleRspBO.class);
//        List<SysUserRoleRspBO> sysUserRoleRspBOS = BeanCopyUtil.copyList(sysUserRoles, SysUserRoleRspBO.class);
//        List<SysRoleMenuRspBO> sysRoleMenuRspBOS = BeanCopyUtil.copyList(sysRoleMenuList, SysRoleMenuRspBO.class);
//        // 查询菜单名称
//        sysRoleMenuRspBOS.forEach(sysRoleMenuRspBO -> {
//            SysMenu sysMenu = sysMenuMapper.selectById(sysRoleMenuRspBO.getMenuId());
//            sysRoleMenuRspBO.setMenuName(sysMenu.getMenuName());
//            sysRoleMenuRspBO.setParentId(sysMenu.getParentId());
//        });
//
//        roleRspBO.setSysUserRoleRspBOList(sysUserRoleRspBOS);
//        roleRspBO.setSysRoleMenuRspBOList(sysRoleMenuRspBOS);
//        return roleRspBO;
    }

    @Override
    public List<com.mayikt.user.model.SysRole> getRoles(Long userId) {
        List<com.mayikt.user.model.SysRole> roles = sysRoleMapper.getRoles(userId);
        return roles;
    }

    @Override
    public void checkRoleDataPermission(Authentication authentication, SysRoleReqBO sysRoleReqBO) {
        AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
        SysUser sysUser = userDetails.getSysUser();
        List<com.mayikt.user.model.SysRole> roleList = sysRoleMapper.getRoles(sysUser.getUserId());

        if (CollectionUtils.isEmpty(roleList)) {
            throw new ApiException(ResultCode.FORBIDDEN);
        }

        Collections.sort(roleList, new Comparator<com.mayikt.user.model.SysRole>() {
            @Override
            public int compare(com.mayikt.user.model.SysRole o1, SysRole o2) {
                return o1.getRoleType().compareTo(o2.getRoleType());
            }
        });
//        SysUser currSysUser = userDetails.getSysUser();
        // 当前用户角色类型
        Integer superRoleType = roleList.get(0).getRoleType();

        // 当前角色新增/修改的角色级别
        Integer roleType = sysRoleReqBO.getRoleType();

        /*if(superRoleType.equals(SystemConstant.ROLE_TYPE_1)){
            if(superRoleType<roleType){
                return;
            }else{
                // 超级管理员只添加下级角色
                throw new ApiException(ResultCode.FORBIDDEN);
            }
        }else */
        if (superRoleType <= roleType) {
            return;
        } else {
            // 不能创建/修改角色成自己更高级别的角色
            throw new ApiException(ResultCode.FORBIDDEN);
        }

    }

}
