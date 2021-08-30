package com.mayikt.service;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayikt.api.SysOrgService;
import com.mayikt.api.SysUserService;
import com.mayikt.bo.AdminUserDetails;
import com.mayikt.bo.MenuTreeRspBO;
import com.mayikt.constant.SystemConstant;
import com.mayikt.mapper.*;
import com.mayikt.model.SysLoginLog;
import com.mayikt.model.SysMenu;
import com.mayikt.model.SysUser;
import com.mayikt.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysUserAdminService sysUserAdminService;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    @Value("${init.password}")
    private String initPassword;
    @Value("${jwt.tokenHead}")
    private String tokenHead;




    @Override
    public Map<String, Object> login(String userName, String password,String ipAddr,
                                     Integer terminalType,String userAgentStr) {
        Map<String, Object> tokenMap = new HashMap<>();
        try {
            UserDetails userDetails = sysUserAdminService.loadUserByUsername(userName,true);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(userDetails);
            AdminUserDetails adminUserDetails = (AdminUserDetails) userDetails;
            List<SysMenu> resourceList = adminUserDetails.getMenus();
            List<MenuTreeRspBO> menuTreeRspBOS = converToMenuTree(resourceList);
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            tokenMap.put("menuTreeList", menuTreeRspBOS);
            adminUserDetails.getSysUser().setPassword("");
            tokenMap.put("sysUser", adminUserDetails.getSysUser());
            tokenMap.put("roles", adminUserDetails.getRoles());
            tokenMap.put("resources", adminUserDetails.getMenus());
            tokenMap.put("sysOrg", adminUserDetails.getSysOrg());
            SysLoginLog record=new SysLoginLog();
            record.setUserId(adminUserDetails.getSysUser().getUserId());
            record.setAccount(adminUserDetails.getSysUser().getAccount());
            record.setUserName(adminUserDetails.getSysUser().getUserName());
            UserAgent userAgent= UserAgentUtil.parse(userAgentStr);
            if(StringUtils.isBlank(userAgentStr)){
                record.setUserAgent(userAgent.getBrowser().getName());
            }else{
                record.setUserAgent(userAgentStr);
            }
            if(terminalType!=null){
                record.setTerminalType(terminalType);
            }else{
                record.setTerminalType(userAgent.isMobile()? SystemConstant.TERMINAL_TYPE_3:SystemConstant.TERMINAL_TYPE_1);
            }
            record.setUserAgent("");
            if(StringUtils.isBlank(ipAddr)){
                HttpServletRequest httpServletRequest = RequestHolder.getHttpServletRequest();
                record.setLoginIp(IpUtil.getIp(httpServletRequest));
            }else{
                record.setLoginIp(ipAddr);
            }
            record.setLoginTime(new Date());
            sysLoginLogMapper.insert(record);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return tokenMap;
    }

    @Override
    public Map<String, Object> getToken(String account , String password, String ipAddr,
                                        Integer terminalType, String userAgentStr) {

        Map<String, Object> tokenMap = new HashMap<>();
        try {
            UserDetails userDetails = sysUserAdminService.loadUserByUsername(account,true);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(userDetails);
            AdminUserDetails adminUserDetails = (AdminUserDetails) userDetails;
            List<SysMenu> resourceList = adminUserDetails.getMenus();
            List<MenuTreeRspBO> menuTreeRspBOS = converToMenuTree(resourceList);
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            tokenMap.put("userId",adminUserDetails.getSysUser().getUserId());
            tokenMap.put("userName",adminUserDetails.getSysUser().getUserName());
            tokenMap.put("roles", adminUserDetails.getRoles());
            tokenMap.put("orgName",adminUserDetails.getSysOrg().getOrgName());
            tokenMap.put("areaCode",adminUserDetails.getSysOrg().getAreaCode());

            SysLoginLog record=new SysLoginLog();
            record.setUserId(adminUserDetails.getSysUser().getUserId());
            record.setAccount(adminUserDetails.getSysUser().getAccount());
            record.setUserName(adminUserDetails.getSysUser().getUserName());
            UserAgent userAgent= UserAgentUtil.parse(userAgentStr);
            if(StringUtils.isBlank(userAgentStr)){
                record.setUserAgent(userAgent.getBrowser().getName());
            }else{
                record.setUserAgent(userAgentStr);
            }
            if(terminalType!=null){
                record.setTerminalType(terminalType);
            }else{
                record.setTerminalType(userAgent.isMobile()?SystemConstant.TERMINAL_TYPE_3:SystemConstant.TERMINAL_TYPE_2);
            }
            record.setUserAgent("");
            if(StringUtils.isBlank(ipAddr)){
                HttpServletRequest httpServletRequest = RequestHolder.getHttpServletRequest();
                record.setLoginIp(IpUtil.getIp(httpServletRequest));
            }else{
                record.setLoginIp(ipAddr);
            }
            record.setLoginTime(new Date());
            sysLoginLogMapper.insert(record);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return tokenMap;
    }

    @Override
    public void logout() {
        AdminUserDetails currentUser = (AdminUserDetails) SecurityUtils.getCurrentUser();
        adminCacheService.deleteRoles(currentUser.getSysUser().getUserId());
        adminCacheService.deleteMenus(currentUser.getSysUser().getUserId());
        adminCacheService.deleteSysUser(currentUser.getSysUser().getAccount());
    }



    @Override
    public SysUser getByUserId(Long userId) {
        return sysUserMapper.getByUserId(userId);
    }

//    @Override
//    public SysOrg getOrgById(Long orgId) {
//        SysOrg sysOrg = adminCacheService.getSysOrg(orgId);
//        if (sysOrg != null) {
//            return sysOrg;
//        }
//        sysOrg = sysOrgMapper.selectById(orgId);
//        adminCacheService.setSysOrg(sysOrg);
//        return sysOrg;
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public CommonResult addUser(SysUserReqBO sysUserReqBO) {
//        SysUser sysUser = BeanCopyUtil.copyObject(sysUserReqBO, SysUser.class);
//        //新增用户
//        sysUser.setPassword(passwordEncoder.encode(initPassword));
//        sysUserMapper.insert(sysUser);
//        Long userId = sysUser.getUserId();
//        return CommonResult.success(userId);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public CommonResult updateUser(SysUserReqBO sysUserReqBO) {
//        SysUser sysUser = BeanCopyUtil.copyObject(sysUserReqBO, SysUser.class);
//        //修改用户
//        sysUserMapper.updateByPrimaryKeySelective(sysUser);
//        Long userId = sysUserReqBO.getUserId();
//        adminCacheService.deleteSysUser(sysUserReqBO.getAccount());
//        return CommonResult.success(null);
//    }
//
//    @Override
//    public CommonResult setUserStanderList(Long userId, List<SysUserStanderReqBO> standerReqBOS) {
//        if (!CollectionUtils.isEmpty(standerReqBOS)) {
//            //先删除后新增区域
//            SysUserStanderExample userStanderExample = new SysUserStanderExample();
//            userStanderExample.createCriteria().andUserIdEqualTo(userId);
//            sysUserStanderMapper.deleteByExample(userStanderExample);
//            List<SysUserStander> standers = standerReqBOS.stream().map(stander -> new SysUserStander(stander.getId(), userId, stander.getAreaCode()))
//                    .collect(Collectors.toList());
//            sysUserStanderMapper.batchInsert(standers);
//        }
//        adminCacheService.deleteUserStanderList(userId);
//        return CommonResult.success(null);
//    }
//
//    @Override
//    public CommonResult setUserRoleList(Long userId, List<SysUserRoleReqBO> roleIds) {
//        //先删除后新增角色
//        SysUserRoleExample userRoleExample = new SysUserRoleExample();
//        userRoleExample.createCriteria().andUserIdEqualTo(userId);
//        sysUserRoleMapper.deleteByExample(userRoleExample);
//        if (!CollectionUtils.isEmpty(roleIds)) {
//            List<SysUserRole> sysUserRoles = roleIds.stream().map(role -> new SysUserRole(role.getUserRoleId(), userId, role.getRoleId()))
//                    .collect(Collectors.toList());
//            sysUserRoleMapper.batchInsert(sysUserRoles);
//        }
//        adminCacheService.deleteRoleList(userId);
//        adminCacheService.deleteResourceList(userId);
//        return CommonResult.success(null);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public CommonResult deleteUser(SysUser sysUser) {
//        Long userId = sysUser.getUserId();
//        sysUserMapper.deleteByPrimaryKey(userId);
//        SysUserRoleExample userRoleExample = new SysUserRoleExample();
//        userRoleExample.createCriteria().andUserIdEqualTo(userId);
//        sysUserRoleMapper.deleteByExample(userRoleExample);
//        SysUserStanderExample userStanderExample = new SysUserStanderExample();
//        userStanderExample.createCriteria().andUserIdEqualTo(userId);
//        sysUserStanderMapper.deleteByExample(userStanderExample);
//        adminCacheService.deleteSysUser(sysUser.getAccount());
//        adminCacheService.deleteRoleList(userId);
//        adminCacheService.deleteUserStanderList(userId);
//        return CommonResult.success(null);
//    }
//
//    @Override
//    public CommonResult resetPass(Long userId, String newPassword) {
//        SysUser record = new SysUser();
//        record.setUserId(userId);
//        if (StringUtils.isEmpty(newPassword)) {
//            record.setPassword(passwordEncoder.encode(initPassword));
//        } else {
//            record.setPassword(passwordEncoder.encode(newPassword));
//        }
//        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
//        int c = sysUserMapper.updateByPrimaryKeySelective(record);
//        if (c > 0) {
//            adminCacheService.deleteSysUser(sysUser.getAccount());
//            return CommonResult.success(null);
//        } else {
//            return CommonResult.failed();
//        }
//    }
//
//    @Override
//    public CommonResult updatePass(SysUser sysUser, String oldPassword, String newPassword) {
//        String oldPass = passwordEncoder.encode(oldPassword);
//        if (!passwordEncoder.matches(oldPassword, sysUser.getPassword())) {
//            return CommonResult.failed("旧密码错误");
//        }
//        return resetPass(sysUser.getUserId(), newPassword);
//    }
//
//    @Override
//    public CommonResult userCheck(Long userId, Integer type, String value) {
//        SysUserExample example = new SysUserExample();
//        SysUserExample.Criteria criteria = example.createCriteria();
//        if (userId != null) {
//            criteria.andUserIdNotEqualTo(userId);
//        }
//        switch (type) {
//            case 1:
//                //账号account
//                criteria.andAccountEqualTo(value);
//                break;
//            case 2:
//                //账号mobile
//                criteria.andMobileEqualTo(value);
//                break;
//            case 3:
//                //账号email
//                criteria.andEmailEqualTo(value);
//                break;
//        }
//        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
//        if (CollectionUtils.isEmpty(sysUsers)) {
//            return CommonResult.success(true);
//        }
//        return CommonResult.success(false);
//    }
//
//    @Override
//    public SysUserRspBO getUserDetailsById(Long userId) {
//        SysUser sysUser = getByUserId(userId);
//        SysUserRspBO sysUserRspBO = BeanCopyUtil.copyObject(sysUser, SysUserRspBO.class);
//        List<SysUserStanderRspBO> standerBOs = BeanCopyUtil.copyList(sysUserAdminService.getStanderList(userId,false), SysUserStanderRspBO.class);
//        sysUserRspBO.setStanderRspBOList(standerBOs);
//        List<SysRoleRspBO> roleRspList = BeanCopyUtil.copyList(sysUserAdminService.getRoleList(userId,false), SysRoleRspBO.class);
//        sysUserRspBO.setRoleRspBOList(roleRspList);
//        SysOrg sysOrg = getOrgById(sysUser.getOrgId());
//        SysOrgRspBO sysOrgRspBO = BeanCopyUtil.copyObject(sysOrg, SysOrgRspBO.class);
//        sysUserRspBO.setSysOrgRspBO(sysOrgRspBO);
//        return sysUserRspBO;
//    }
//
    private List<MenuTreeRspBO> converToMenuTree(List<SysMenu> resourceList) {
        if (CollectionUtils.isEmpty(resourceList)) {
            return new ArrayList<MenuTreeRspBO>();
        }
        List<MenuTreeRspBO> menuTreeRspBOList=resourceList.stream()
                .filter(menu -> menu.getParentId().equals(0L) && menu.getShowFlag().equals(1))
                .map(sysMenu->BeanCopyUtil.copyObject(sysMenu, MenuTreeRspBO.class))
                .map(sysMenu->setChildrenMenuTree(sysMenu,resourceList))
                .collect(Collectors.toList());
        return menuTreeRspBOList;
    }

    private MenuTreeRspBO setChildrenMenuTree(MenuTreeRspBO sysMenu, List<SysMenu> resourceList) {
        List<MenuTreeRspBO> children = resourceList.stream()
                .filter(menu -> menu.getParentId().equals(sysMenu.getMenuId()) && menu.getShowFlag().equals(1))
                .map(menu -> setChildrenMenuTree(BeanCopyUtil.copyObject(menu, MenuTreeRspBO.class), resourceList))
                .collect(Collectors.toList());
        sysMenu.setChildren(children);
        return sysMenu;
    }
//
//    @Override
//    public void checkUserDataPermission(Authentication authentication, SysUserReqBO sysUserReqBO,String type) {
//        AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
//        List<SysRole> roleList = userDetails.getRoleList();
//        Collections.sort(roleList, new Comparator<SysRole>() {
//            @Override
//            public int compare(SysRole o1, SysRole o2) {
//                return o1.getRoleType().compareTo(o2.getRoleType());
//            }
//        });
//        SysOrg currSysOrg = userDetails.getSysOrg();
//        SysUser currSysUser = userDetails.getSysUser();
//        Integer superRoleType = roleList.get(0).getRoleType();
//        Long orgId = sysUserReqBO.getOrgId();
//        if (orgId == null && sysUserReqBO.getUserId() != null) {
//            SysUser user = getByUserId(sysUserReqBO.getUserId());
//            if (user == null) {
//                throw new ApiException("用户不存在");
//            }
//            orgId = user.getOrgId();
//        }
//        SysOrg sysOrg = getOrgById(orgId);
//        if (sysOrg == null) {
//            throw new ApiException("机构不存在");
//        }
//        String areaCode = sysOrg.getAreaCode();
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_1)) {
//            //超级管理员有所有权限
//            return;
//        }
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_2)) {
//            //区域管理员
//            if (!areaCode.equals(currSysOrg.getAreaCode())) {
//                throw new ApiException(ResultCode.FORBIDDEN);
//            }
//        }
//        List<SysOrg> childrenOrgs = sysOrgService.getChildOrgs(currSysOrg);
//        List<Long> orgIds = childrenOrgs.stream().map(item -> item.getOrgId()).collect(Collectors.toList());
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_3)) {
//            //单位管理员 操作本单位和子单位的
//            if (!orgIds.contains(orgId)) {
//                throw new ApiException(ResultCode.FORBIDDEN);
//            }
//        }
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_4)) {
//            if (sysUserReqBO.getUserId() == null) {
//                throw new ApiException(ResultCode.FORBIDDEN);
//            }
//            //普通人员,可以查看本部门的人员,只能操作自己的数据
//            if("query".equals(type)){
//                if(!orgIds.contains(orgId)){
//                    throw new ApiException(ResultCode.FORBIDDEN);
//                }
//            }else{
//                if (!sysUserReqBO.getUserId().equals(currSysUser.getUserId())) {
//                    throw new ApiException(ResultCode.FORBIDDEN);
//                }
//            }
//
//        }
//    }
//
//    @Override
//    public List<SysUser> listUser(SysUserReqBO sysUserReqBO) {
//        SysUserExample example = new SysUserExample();
//        SysUserExample.Criteria criteria = example.createCriteria();
////        criteria.andStatusEqualTo(1);
//        if (!StringUtils.isBlank(sysUserReqBO.getUserName())) {
//            criteria.andUserNameLike("%" + sysUserReqBO.getUserName() + "%");
//        }
//        return sysUserMapper.selectByExample(example);
//    }
//
//    @Override
//    public List<SysRoleRspBO> getRoleList(Long userId) {
//        AdminUserDetails userDetails = (AdminUserDetails)SecurityUtils.getCurrentUser();
//        List<SysRole> roleList = userDetails.getRoleList();
//        Collections.sort(roleList, new Comparator<SysRole>() {
//            @Override
//            public int compare(SysRole o1, SysRole o2) {
//                return o1.getRoleType().compareTo(o2.getRoleType());
//            }
//        });
//        SysOrg currSysOrg = userDetails.getSysOrg();
//        SysUser currSysUser = userDetails.getSysUser();
//        Integer superRoleType = roleList.get(0).getRoleType();
//
//        //查询当前用户的角色
//        SysUserRoleExample userRoleExample=new SysUserRoleExample();
//        userRoleExample.createCriteria().andUserIdEqualTo(userId);
//        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(userRoleExample);
//        List<Long> roleIds=sysUserRoles.stream().map(item->item.getRoleId()).collect(Collectors.toList());
//        SysRoleExample roleExample=new SysRoleExample();
//
//        roleExample.createCriteria().andStatusEqualTo("0").andRoleTypeGreaterThan(superRoleType);
//        roleExample.or().andRoleIdIn(roleIds);
//        roleExample.setOrderByClause("role_type asc");
//        List<SysRole> sysRoles = sysRoleMapper.selectByExample(roleExample);
//
//
//        if(userId!=null && userId.equals(currSysUser.getUserId())){
//            List<SysRole> currRoles=userDetails.getRoleList();
//            if(!CollectionUtils.isEmpty(currRoles)){
//                for (SysRole currRole : currRoles) {
//                    if(!sysRoles.contains(currRole)){
//                        sysRoles.add(currRole);
//                    }
//                }
//            }
//        }
//        List<SysRoleRspBO> sysRoleRspBOs=BeanCopyUtil.copyList(sysRoles,SysRoleRspBO.class);
//
//        return sysRoleRspBOs;
//    }
//
//    @Override
//    public Integer statMonthLoginCount() {
//        AdminUserDetails userDetails = (AdminUserDetails) SecurityUtils.getCurrentUser();
//        Long userId=userDetails.getSysUser().getUserId();
//        SysLoginLogExample example=new SysLoginLogExample();
//        Date start= DateUtil.beginOfMonth(new Date());
//        Date end= DateUtil.endOfMonth(new Date());
//        example.createCriteria().andUserIdEqualTo(userId).andLoginTimeBetween(start,end);
//        Long count = sysLoginLogMapper.countByExample(example);
//        return count.intValue();
//    }

    @Override
    public List<SysUser> selectList(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUserName, userName);
        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        return sysUsers;
    }
}
