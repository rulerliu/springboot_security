package com.mayikt.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayikt.user.model.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser getByUserId(Long userId);
}
