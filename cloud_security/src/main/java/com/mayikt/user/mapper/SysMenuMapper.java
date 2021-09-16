package com.mayikt.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayikt.user.model.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> getMenus(Long userId);
}
