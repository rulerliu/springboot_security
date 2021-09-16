package com.mayikt.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayikt.user.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<com.mayikt.user.model.SysRole> {
    List<SysRole> getRoles(Long userId);
}
