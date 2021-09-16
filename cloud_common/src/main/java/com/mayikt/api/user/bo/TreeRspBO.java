package com.mayikt.api.user.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mayikt.constant.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor()
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TreeRspBO implements Serializable {
    private Long id;
    private Long parentId;
    private String label;
    private Integer flag;
    private Integer level;
    private Long fileId;
    private String fileName;
    private String remark;
    private Long stageId;
    private List<TreeRspBO> children;

    public TreeRspBO(Long id, Long parentId, String label, Integer flag, Integer level, List<TreeRspBO> children) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.flag = flag;
        this.level = level;
        this.children = children;
    }

    public TreeRspBO(Long id, Long parentId, String label, Integer flag, Integer level, String remark, List<TreeRspBO> children) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.flag = flag;
        this.level = level;
        this.remark = remark;
        this.children = children;
    }

    public TreeRspBO(Long id, Long parentId, String label, Long fileId, Integer flag, Integer level, List<TreeRspBO> children) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.fileId = fileId;
        this.flag = flag;
        this.level = level;
        this.children = children;
    }
    public TreeRspBO(Long id, Long parentId, String label, Long fileId, Integer flag, Integer level,Long stageId, List<TreeRspBO> children) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.fileId = fileId;
        this.flag = flag;
        this.level = level;
        this.stageId = stageId;
        this.children = children;
    }

    public TreeRspBO(Long id, Long parentId, String label, Long fileId, String fileName, Integer flag, Integer level, List<TreeRspBO> children) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.fileId = fileId;
        this.flag = flag;
        this.level = level;
        this.children = children;
    }

    public static ResultCheckRspBO check(TreeRspBO treeRspBO, TreeRspBO tmpTreeRspBO, ResultCheckRspBO checkRspBO) {
        List<TreeRspBO> allTreeList = convertTreeToList(tmpTreeRspBO);
        checkRspBO.setAllFileCount(allTreeList.size());
        //比较每个节点是否相等
        if (treeRspBO == null || !tmpTreeRspBO.getLabel().equals(treeRspBO.getLabel())) {
            checkRspBO.addErrorDir(getFullPath(tmpTreeRspBO.getId(), allTreeList), tmpTreeRspBO.getLabel());
        }
        List<TreeRspBO> tmpChildren = tmpTreeRspBO.getChildren();
        List<TreeRspBO> children = treeRspBO == null ? null : treeRspBO.getChildren();

        simpleCheckChildren(children, tmpChildren, checkRspBO, allTreeList);
        checkRspBO.endCheck();
        return checkRspBO;
    }


    private static String getFullPath(Long id, List<TreeRspBO> allTreeList) {
        List<String> paths = new ArrayList<>();
        //查找到当前的tree
        TreeRspBO currNode = allTreeList.stream().filter(item -> item.getId().equals(id)).findFirst().get();
        paths.add(currNode.getLabel());
        getAllParentNode(paths, currNode, allTreeList);
        Collections.reverse(paths);
        return paths.stream().collect(Collectors.joining("/"));
    }

    private static List<TreeRspBO> convertTreeToList(TreeRspBO treeRspBO) {
        List<TreeRspBO> treeRspBOS = new ArrayList<>();
        treeRspBOS.add(new TreeRspBO(treeRspBO.getId(), treeRspBO.getParentId(), treeRspBO.getLabel(),
                treeRspBO.getFlag(), treeRspBO.getLevel(), null));
        addChildrenToList(treeRspBOS, treeRspBO.getChildren());

        return treeRspBOS;
    }

    private static void addChildrenToList(List<TreeRspBO> resultList, List<TreeRspBO> childrens) {
        if (CollectionUtils.isEmpty(childrens)) {
            return;
        }
        for (TreeRspBO treeRspBO : childrens) {
            resultList.add(new TreeRspBO(treeRspBO.getId(), treeRspBO.getParentId(), treeRspBO.getLabel(),
                    treeRspBO.getFlag(), treeRspBO.getLevel(), null));
            addChildrenToList(resultList, treeRspBO.getChildren());
        }
    }

    private static void getAllParentNode(List<String> paths, final TreeRspBO currNode, List<TreeRspBO> allTreeList) {
        if (currNode.getParentId().equals(0L)) {
            return;
        }
        TreeRspBO parentNode = allTreeList.stream().filter(item -> item.getId().equals(currNode.getParentId())).findFirst().get();
        paths.add(parentNode.getLabel());
        getAllParentNode(paths, parentNode, allTreeList);
    }


    private static void simpleCheckChildren(List<TreeRspBO> children, List<TreeRspBO> tmpChildren, ResultCheckRspBO checkRspBO,
                                            List<TreeRspBO> allTreeList) {
        if (tmpChildren == null && children == null) {
            return;
        }
        if (tmpChildren == null && children != null) {
            return;
        }
        if ((tmpChildren != null && children == null)) {
            //缺失
            for (TreeRspBO tmpChild : tmpChildren) {
                if (tmpChild.getFlag().equals(SystemConstant.FILE_TYPE_DIR)) {
                    checkRspBO.addErrorDir(getFullPath(tmpChild.getId(), allTreeList), tmpChild.getLabel());
                } else {
                    checkRspBO.addErrorFile(getFullPath(tmpChild.getId(), allTreeList), tmpChild.getLabel());
                }
            }
            return ;
        }
        //遍历模板树 查找缺失的文件或目录
        for (TreeRspBO tmpChild : tmpChildren) {
            Optional<TreeRspBO> first= children.stream().filter(item -> item.getLabel().equals(tmpChild.getLabel()) && item.getFlag().equals(tmpChild.getFlag()))
                        .findFirst();
            TreeRspBO findCurrNode = null;
            if (!first.isPresent()) {
                //不存在表示缺失了
                if (tmpChild.getFlag().equals(SystemConstant.FILE_TYPE_DIR)) {
                    checkRspBO.addErrorDir(getFullPath(tmpChild.getId(), allTreeList), tmpChild.getLabel());
                } else {
                    checkRspBO.addErrorFile(getFullPath(tmpChild.getId(), allTreeList), tmpChild.getLabel());
                }
            } else {
                findCurrNode = first.get();
            }
            if (!CollectionUtils.isEmpty(tmpChild.getChildren())) {
                simpleCheckChildren(findCurrNode == null ? null : findCurrNode.getChildren(),tmpChild.getChildren(),
                        checkRspBO, allTreeList);
            }
        }
    }
}

