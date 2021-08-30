package com.mayikt.service;

import com.mayikt.api.SysOrgService;
import com.mayikt.api.SysUserService;
import com.mayikt.bo.OrgTreeRspBO;
import com.mayikt.bo.SysOrgReqBO;
import com.mayikt.mapper.SysOrgMapper;
import com.mayikt.model.SysOrg;
import com.mayikt.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("sysOrgService")
public class SysOrgServiceImpl implements SysOrgService {
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<OrgTreeRspBO> listOrgTree(SysOrgReqBO reqBO) {
        return null;
//        SysOrgExample example = new SysOrgExample();
//        example.setOrderByClause("sort asc");
//        SysOrgExample.Criteria criteria = example.createCriteria();
//        List<SysOrg> orgList = new ArrayList<>();
//        // 权限
//        AdminUserDetails currentUser = (AdminUserDetails) SecurityUtils.getCurrentUser();
//        Integer superRoleType = SecurityUtils.getSuperRoleType();
//        SysOrg currSysOrg = currentUser.getSysOrg();
//        String areaCode = currSysOrg.getAreaCode();
//        //超级管理员，所有数据
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_1)) {
//            orgList = sysOrgMapper.selectByExample(example);
//        }
//        //区域管理员，本区域数据
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_2)) {
//            criteria.andAreaCodeEqualTo(areaCode);
//            orgList = sysOrgMapper.selectByExample(example);
//        }
//        // 单位管理员，本单位和子单位数据
//        // 普通人员，本单位数据
//        List<Long> aa = new ArrayList<>();
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_3) || superRoleType.equals(SystemConstant.ROLE_TYPE_4)) {
//            List<SysOrg> allOrgList = list(null);
//            // 父节点
//            List<SysOrg> parentOrgs = getParentOrgs(currSysOrg.getParentId(), allOrgList);
//            if (!CollectionUtils.isEmpty(parentOrgs)) {
//                orgList.addAll(parentOrgs);
//            }
//            // 子节点
//            List<SysOrg> childrenOrgs = getChildOrgs(currSysOrg, allOrgList, true);
//            if (!CollectionUtils.isEmpty(childrenOrgs)) {
//                orgList.addAll(childrenOrgs);
//            }
//            // 兄弟节点
//            List<SysOrg> brotherOrgs = getBrotherOrgs(currSysOrg.getOrgId(), allOrgList);
//            if (!CollectionUtils.isEmpty(brotherOrgs)) {
//                getBrotherChildOrgs(brotherOrgs, allOrgList, orgList, aa);
//            } else {
//                aa.add(currSysOrg.getOrgId());
//            }
//
//        }
//
//        if (CollectionUtils.isEmpty(orgList)) {
//            return new ArrayList<>();
//        }
//        orgList = removeDuplicateKeepOrder(orgList);
//
//        SysUserReqBO sysUserReqBO = new SysUserReqBO();
//        sysUserReqBO.setUserName(reqBO.getOrgName());
//        List<SysUser> sysUsers = sysUserService.listUser(sysUserReqBO);
//        Map<Long, List<SysUser>> userMap = new HashMap<>();
//        if (!CollectionUtils.isEmpty(sysUsers)) {
//            userMap = sysUsers.stream().collect(Collectors.groupingBy(SysUser::getOrgId));
//        }
//
//        // 有orgName查询条件
//        if (!StringUtils.isBlank(reqBO.getOrgName())) {
//            criteria.andOrgNameLike("%" + reqBO.getOrgName() + "%");
//            // 多查一次所有叶子结点
//            List<SysOrg> leafOrgs = sysOrgMapper.selectByExample(example);
//
//            // 2.所有的父节点
//            Set<SysOrg> rootOrgs = new HashSet<>();
//            for (SysOrg leafOrg : leafOrgs) {
//                findParents(leafOrg.getParentId(), orgList, rootOrgs);
//            }
//            rootOrgs.addAll(leafOrgs);
//            return listOrgTree(new ArrayList<>(rootOrgs), userMap, aa, reqBO.getType() == 1 ? false : true, reqBO.getType());
//        }
//
//        if (reqBO.getOrgId() != null) { // 当前机构 以及机构的子机构
//            // 多查一次
//            SysOrg org = sysOrgMapper.selectByPrimaryKey(reqBO.getOrgId());
//            List<SysOrg> allOrgList = list(null);
//            List<SysOrg> childrenOrgs = getChildOrgs(org, allOrgList, true);
//            return listOrgTree(org, new ArrayList<>(childrenOrgs), userMap, aa, reqBO.getType() == 1 ? false : true, reqBO.getType());
//        }
//
//        if (reqBO.getType() != null) {
//            return listOrgTree(orgList, userMap, aa, reqBO.getType() == 1 ? false : true, reqBO.getType());
//        }
//
//        return listOrgTree(orgList, userMap, aa, true, reqBO.getType());
    }

    /**
     * @param orgList
     * @return
     */
    private List<OrgTreeRspBO> listOrgTree(SysOrg sysOrg, List<SysOrg> orgList, Map<Long, List<SysUser>> userMap,
                                           List<Long> aa, boolean includeUser, Integer type) {
        List<OrgTreeRspBO> treeList = new ArrayList<>();
        int level = 1;
        treeList.add(new OrgTreeRspBO(sysOrg.getOrgId(), sysOrg.getParentId(), sysOrg.getOrgName(),
                1, level++, "bm" + sysOrg.getOrgId(),
                getDisabled(sysOrg.getOrgType(), type), null));

        // 为顶层组织设置子组织，getChild是递归调用的
        for (OrgTreeRspBO treeRspBO : treeList) {
            treeRspBO.setChildren(getChild(treeRspBO.getId(), orgList, level, type));
        }

        int userLevel = 1;
        if (includeUser) {
            setSysUsers(treeList, userMap, userLevel, aa, type);
        }
        return treeList;
    }

    /**
     * @param orgList
     * @return
     */
    private List<OrgTreeRspBO> listOrgTree(List<SysOrg> orgList, Map<Long, List<SysUser>> userMap,
                                        List<Long> aa, boolean includeUser, Integer type) {
        List<OrgTreeRspBO> treeList = new ArrayList<>();
        int level = 1;
        // 先找到所有的一级组织
        for (SysOrg sysOrg : orgList) {
            // 一级组织parentId是0
            if (sysOrg.getParentId() == 0) {
                treeList.add(new OrgTreeRspBO(sysOrg.getOrgId(), sysOrg.getParentId(), sysOrg.getOrgName(),
                        1, level++, "bm" + sysOrg.getOrgId(),
                        getDisabled(sysOrg.getOrgType(), type), null));
            }
        }

        // 为一级组织设置子组织，getChild是递归调用的
        for (OrgTreeRspBO treeRspBO : treeList) {
            treeRspBO.setChildren(getChild(treeRspBO.getId(), orgList, level, type));
        }

        int userLevel = 1;
        if (includeUser) {
            setSysUsers(treeList, userMap, userLevel, aa, type);
        }
        return treeList;
    }

    public static List<SysOrg> removeDuplicateKeepOrder(List<SysOrg> list) {
        Set<SysOrg> set = new HashSet();
        List<SysOrg> newList = new ArrayList<>();
        for (SysOrg element : list) {
            //set能添加进去就代表不是重复的元素
            if (set.add(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    /**
     * 根据叶子结点找到根节点
     *
     * @param parentId
     * @param leafOrgs
     * @param rootOrgs
     */
    private void findParents(Long parentId, List<SysOrg> leafOrgs, Set<SysOrg> rootOrgs) {
        List<SysOrg> result = leafOrgs.stream().filter(sysOrg -> sysOrg.getOrgId().equals(parentId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        SysOrg sysOrg = result.get(0);

        if (sysOrg != null) {
            rootOrgs.add(sysOrg);
            if (sysOrg.getParentId() != 0L) {
                findParents(sysOrg.getParentId(), leafOrgs, rootOrgs);
            }
        }
    }

    private void setSysUsers(List<OrgTreeRspBO> treeList, Map<Long, List<SysUser>> userMap, int level,
                             List<Long> aa, Integer type) {
        Iterator<OrgTreeRspBO> iterator = treeList.iterator();
        while (iterator.hasNext()) {
            OrgTreeRspBO treeRspBO = iterator.next();
            level++;
            List<SysUser> sysUsers = userMap.get(treeRspBO.getId());
            if (!CollectionUtils.isEmpty(sysUsers) && treeRspBO.getFlag() == 1) {
                for (SysUser sysUser : sysUsers) {
                    if (CollectionUtils.isEmpty(treeRspBO.getChildren())) {
                        treeRspBO.setChildren(new ArrayList<>());
                    }
                    if (CollectionUtils.isEmpty(aa)) {
                        treeRspBO.getChildren().add(new OrgTreeRspBO(sysUser.getUserId(), sysUser.getUserId(),
                                sysUser.getUserName(), 2, level, "yh" + sysUser.getUserId(),
                                false, null));
                    } else {
                        if (aa.contains(sysUser.getOrgId())) {
                            treeRspBO.getChildren().add(new OrgTreeRspBO(sysUser.getUserId(), sysUser.getUserId(),
                                    sysUser.getUserName(), 2, level, "yh" + sysUser.getUserId(),
                                    false, null));
                        }
                    }
                }
            }

            if (!CollectionUtils.isEmpty(treeRspBO.getChildren())) {
                setSysUsers(treeRspBO.getChildren(), userMap, level, aa, type);
            }
        }
    }

    /**
     * 递归查找子组织
     *
     * @param id      一级组织id
     * @param orgList 要查找的列表
     * @return
     */
    private List<OrgTreeRspBO> getChild(Long id, List<SysOrg> orgList, int level, Integer type) {
        // 子组织
        List<OrgTreeRspBO> childList = new ArrayList<>();
        for (SysOrg sysOrg : orgList) {
            // 遍历所有节点，将父组织id与传过来的id比较
            if (id.equals(sysOrg.getParentId())) {
                childList.add(new OrgTreeRspBO(sysOrg.getOrgId(), sysOrg.getParentId(), sysOrg.getOrgName(),
                        1, level++, "bm" + sysOrg.getOrgId(),
                        getDisabled(sysOrg.getOrgType(), type), null));
            }
        }

        // 把子组织的子组织再循环一遍
        for (OrgTreeRspBO subTreeRspBO : childList) {
            // org_type不为3的组织还有子组织
            if (subTreeRspBO.getFlag() != 3) {
                // 递归
                subTreeRspBO.setChildren(getChild(subTreeRspBO.getId(), orgList, level, type));
            }
        }

        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    @Override
    public void checkDataPermission(SysOrgReqBO sysOrgReqBO, String type) {
//        AdminUserDetails userDetails = (AdminUserDetails) SecurityUtils.getCurrentUser();
//        List<SysRole> roleList = userDetails.getRoles();
//        Collections.sort(roleList, new Comparator<SysRole>() {
//            @Override
//            public int compare(SysRole o1, SysRole o2) {
//                return o1.getRoleType().compareTo(o2.getRoleType());
//            }
//        });
//        SysOrg currSysOrg = userDetails.getSysOrg();
//        Integer superRoleType = roleList.get(0).getRoleType();
//        String areaCode = sysOrgReqBO.getAreaCode();
//        if (areaCode == null && sysOrgReqBO.getOrgId() != null) {
//            SysOrg org = sysUserService.getOrgById(sysOrgReqBO.getOrgId());
//            if (org == null) {
//                throw new ApiException("机构不存在");
//            }
//            areaCode = org.getAreaCode();
//        }
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
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_3)) {
//            //单位管理员 操作本单位和子单位的
//            List<SysOrg> childrenOrgs = getChildOrgs(currSysOrg);
//            List<Long> orgIds = childrenOrgs.stream().map(item -> item.getOrgId()).collect(Collectors.toList());
//            if (!orgIds.contains(sysOrgReqBO.getOrgId())) {
//                throw new ApiException(ResultCode.FORBIDDEN);
//            }
//        }
//        if (superRoleType.equals(SystemConstant.ROLE_TYPE_4)) {
//            //普通人员 可查看本单位和子单位 不能操作机构
//            if ("query".equals(type)) {
//                List<SysOrg> childrenOrgs = getChildOrgs(currSysOrg);
//                List<Long> orgIds = childrenOrgs.stream().map(item -> item.getOrgId()).collect(Collectors.toList());
//                if (!orgIds.contains(sysOrgReqBO.getOrgId())) {
//                    throw new ApiException(ResultCode.FORBIDDEN);
//                }
//            } else {
//                throw new ApiException(ResultCode.FORBIDDEN);
//            }
//        }
    }

    @Override
    public List<SysOrg> getChildOrgs(SysOrg sysOrg) {
        List<SysOrg> allOrgList = list(null);
        return getChildOrgs(sysOrg, allOrgList, true);
    }

    @Override
    public List<SysOrg> getChildOrgs(SysOrg sysOrg, List<SysOrg> allOrgList, boolean flag) {
        List<SysOrg> childOrgs = new ArrayList<>();
        if (flag) {
            childOrgs.add(sysOrg);
        }
        setChildOrgs(sysOrg, allOrgList, childOrgs);
        return childOrgs;
    }

    private void setChildOrgs(SysOrg sysOrg, List<SysOrg> allOrgList, List<SysOrg> childOrgs) {
        List<SysOrg> childs = allOrgList.stream().filter(org -> org.getParentId().equals(sysOrg.getOrgId()))
                .collect(Collectors.toList());
        childOrgs.addAll(childs);
        if (CollectionUtils.isEmpty(childs)) {
            return;
        }
        childs.stream().forEach(item -> setChildOrgs(item, allOrgList, childOrgs));
    }

    /**
     * 根据叶子结点找到根节点
     *
     * @param parentId
     * @param allOrgList
     */
    private List<SysOrg> getParentOrgs(Long parentId, List<SysOrg> allOrgList) {
        List<SysOrg> parentOrgs = new ArrayList<>();
        setParentOrgs(parentId, allOrgList, parentOrgs);
        return parentOrgs;
    }

    private void setParentOrgs(Long parentId, List<SysOrg> allOrgList, List<SysOrg> parentOrgs) {
        List<SysOrg> result = allOrgList.stream().filter(sysOrg -> sysOrg.getOrgId().equals(parentId))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        SysOrg sysOrg = result.get(0);
        if (sysOrg != null) {
            parentOrgs.add(sysOrg);
            if (sysOrg.getParentId() != 0L) {
                setParentOrgs(sysOrg.getParentId(), allOrgList, parentOrgs);
            }
        }
    }

    /**
     * 找兄弟节点
     *
     * @param parentId
     * @param allOrgList
     * @return
     */
    private List<SysOrg> getBrotherOrgs(Long parentId, List<SysOrg> allOrgList) {
        List<SysOrg> brotherOrgs = allOrgList.stream().filter(sysOrg -> sysOrg.getParentId().equals(parentId))
                .collect(Collectors.toList());
        return brotherOrgs;
    }

    /**
     * 找兄弟节点的子节点
     *
     * @param brotherOrgs
     * @param allOrgList
     * @param orgList
     */
    private void getBrotherChildOrgs(List<SysOrg> brotherOrgs, List<SysOrg> allOrgList, List<SysOrg> orgList, List<Long> aa) {
        orgList.addAll(brotherOrgs);
        for (SysOrg brotherOrg : brotherOrgs) {
            aa.add(brotherOrg.getOrgId());
            aa.add(brotherOrg.getParentId());
            List<SysOrg> brotherChildOrgs = getChildOrgs(brotherOrg, allOrgList, false);
            if (!CollectionUtils.isEmpty(brotherChildOrgs)) {
                // 兄弟节点的子节点
                getBrotherChildOrgs(brotherChildOrgs, allOrgList, orgList, aa);
            }
        }
    }

    public List<SysOrg> list(SysOrgReqBO reqBO) {
        return sysOrgMapper.selectList(null);
    }

    @Override
    public SysOrg selectById(Long orgId) {
        return sysOrgMapper.selectById(orgId);
    }

    @Override
    public int upadteOrgById(SysOrg sysOrg) {
        return sysOrgMapper.updateById(sysOrg);
    }

    private Boolean getDisabled(Integer orgType, Integer type) {
        if (type == null) {
            return orgType == 1 ? false : true;
        }
        return type == 2 ? false : orgType == 1 ? false : true;
    }
}
