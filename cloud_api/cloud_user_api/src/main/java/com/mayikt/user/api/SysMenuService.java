package com.mayikt.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mayikt.api.user.bo.TreeRspBO;
import com.mayikt.user.bo.SysMenuReqBO;
import com.mayikt.user.model.SysMenu;

import java.util.List;


public interface SysMenuService extends IService<SysMenu> {
    int deleteById(Long parentId);
    List<TreeRspBO> listMenuTree(SysMenuReqBO reqBO);
    List<SysMenu> selectList(Integer status);
    List<SysMenu> getMenus(Long userId);
}
