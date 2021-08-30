package com.mayikt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayikt.api.SysMenuService;
import com.mayikt.bo.SysMenuReqBO;
import com.mayikt.bo.TreeRspBO;
import com.mayikt.mapper.SysMenuMapper;
import com.mayikt.model.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public int deleteById(Long parentId) {
        List<SysMenu> sysMenus = selectList(0);
        if (CollectionUtils.isEmpty(sysMenus)) {
            return 0;
        }

        Set<SysMenu> leafMenus = new HashSet<>();
        findChildren(parentId, sysMenus, leafMenus);

        List<Long> sysMenuIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(leafMenus)) {
            sysMenuIds = leafMenus.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        }
        sysMenuIds.add(parentId);
        sysMenuMapper.deleteBatchIds(sysMenuIds);
        return 1;
    }

    private void findChildren(Long parentId, List<SysMenu> sysMenus, Set<SysMenu> leafMenus) {
        List<SysMenu> childrenList = sysMenus.stream().filter(condition -> condition.getParentId().equals(parentId)
        ).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(childrenList)) {
            leafMenus.addAll(childrenList);
            for (SysMenu sysMenu : childrenList) {
                findChildren(sysMenu.getMenuId(), childrenList, leafMenus);
            }
        }
    }

    @Override
    public List<TreeRspBO> listMenuTree(SysMenuReqBO reqBO) {
        List<SysMenu> sysMenus = selectList(0);
        if (CollectionUtils.isEmpty(sysMenus)) {
            return null;
        }
        return generateMenuTree(sysMenus);

    }

    @Override
    public List<SysMenu> selectList(Integer status) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysMenu::getStatus, status);
        queryWrapper.orderByAsc("sort");
        return sysMenuMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysMenu> getMenus(Long userId) {
        return sysMenuMapper.getMenus(userId);
    }

    /**
     *
     * @param menuList
     * @return
     */
    private List<TreeRspBO> generateMenuTree(List<SysMenu> menuList) {
        List<TreeRspBO> treeList = new ArrayList<>();
        // 先找到所有的一级菜单
        for (SysMenu sysMenu : menuList) {
            // 一级菜单parentId是0
            if (sysMenu.getParentId() == 0) {
                treeList.add(new TreeRspBO(sysMenu.getMenuId(), sysMenu.getParentId(), sysMenu.getMenuName(), 1,null, null));
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (TreeRspBO treeRspBO : treeList) {
            treeRspBO.setChildren(getChild(treeRspBO.getId(), menuList));
        }
        return treeList;
    }

    /**
     * 递归查找子菜单
     *
     * @param id      一级菜单id
     * @param menuList 要查找的列表
     * @return
     */
    private List<TreeRspBO> getChild(Long id, List<SysMenu> menuList) {
        // 子菜单
        List<TreeRspBO> childList = new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (id.equals(sysMenu.getParentId())) {
                childList.add(new TreeRspBO(sysMenu.getMenuId(), sysMenu.getParentId(), sysMenu.getMenuName(), sysMenu.getMenuType(),null, null));
            }
        }

        // 把子菜单的子菜单再循环一遍
        for (TreeRspBO subTreeRspBO : childList) {
            // menu_type不为3的菜单还有子菜单
            if (subTreeRspBO.getFlag() != 3) {
                // 递归
                subTreeRspBO.setChildren(getChild(subTreeRspBO.getId(), menuList));
            }
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
