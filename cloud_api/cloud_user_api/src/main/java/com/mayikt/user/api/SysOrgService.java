package com.mayikt.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mayikt.bo.OrgTreeRspBO;
import com.mayikt.user.bo.SysOrgReqBO;
import com.mayikt.user.model.SysOrg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysOrgService extends IService<SysOrg> {

    List<OrgTreeRspBO> listOrgTree(SysOrgReqBO sysOrgReqBO);
    List<SysOrg> listSysOrg(SysOrgReqBO reqBO);

    SysOrg selectById(Long orgId);

    void checkDataPermission(SysOrgReqBO sysOrgReqBO,String type);

    List<SysOrg> getChildOrgs(SysOrg currSysOrg);
    List<SysOrg> getChildOrgs(SysOrg sysOrg, List<SysOrg> allOrgList, boolean flag);

    int upadteOrgById(SysOrg sysOrg);

}
