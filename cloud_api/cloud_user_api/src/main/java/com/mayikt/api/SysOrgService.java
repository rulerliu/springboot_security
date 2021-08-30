package com.mayikt.api;

import com.mayikt.bo.OrgTreeRspBO;
import com.mayikt.bo.SysOrgReqBO;
import com.mayikt.model.SysOrg;

import java.util.List;

public interface SysOrgService {

    List<OrgTreeRspBO> listOrgTree(SysOrgReqBO sysOrgReqBO);
    List<SysOrg> list(SysOrgReqBO reqBO);

    SysOrg selectById(Long orgId);

    void checkDataPermission(SysOrgReqBO sysOrgReqBO,String type);

    List<SysOrg> getChildOrgs(SysOrg currSysOrg);
    List<SysOrg> getChildOrgs(SysOrg sysOrg, List<SysOrg> allOrgList, boolean flag);

    int upadteOrgById(SysOrg sysOrg);

}
