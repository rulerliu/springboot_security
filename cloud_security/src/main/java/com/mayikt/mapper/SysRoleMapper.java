package com.mayikt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayikt.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> getRoles(Long userId);
}
