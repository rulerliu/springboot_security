package com.mayikt.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mayikt.user.api.SysUserRoleService;
import com.mayikt.user.mapper.SysUserRoleMapper;
import com.mayikt.user.model.SysUserRole;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
