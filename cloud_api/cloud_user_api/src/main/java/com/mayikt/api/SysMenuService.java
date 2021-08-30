package com.mayikt.api;

import com.mayikt.bo.SysMenuReqBO;
import com.mayikt.bo.TreeRspBO;
import com.mayikt.model.SysMenu;

import java.util.List;


public interface SysMenuService {
    int deleteById(Long parentId);
    List<TreeRspBO> listMenuTree(SysMenuReqBO reqBO);
    List<SysMenu> selectList(Integer status);
    List<SysMenu> getMenus(Long userId);
}
