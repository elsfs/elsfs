package org.elsfs.admin.app.command.query;

import lombok.RequiredArgsConstructor;
import org.elsfs.admin.infrastructure.dataobject.SysDeptDB;
import org.elsfs.admin.infrastructure.repository.SysDepRepository;
import org.elsfs.core.dto.MultiResponse;
import org.elsfs.core.util.lang.tree.Tree;
import org.elsfs.core.util.lang.tree.TreeNode;
import org.elsfs.core.util.lang.tree.TreeUtil;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zeng
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class DeptListDeptTreesQryExe {

    private final SysDepRepository sysDepRepository;

    public MultiResponse<Tree<String>> execute() {
        List<SysDeptDB> list = sysDepRepository.findAll();
        return MultiResponse.of(getDeptTree(list, "0"));
    }

    /**
     * 构建部门树
     * @param depts 部门
     * @param parentId 父级id
     * @return
     */
    private List<Tree<String>> getDeptTree(List<SysDeptDB> depts, String parentId) {
        List<TreeNode<String>> collect = depts.stream()
            .filter(dept -> dept.getDeptId().equals(dept.getParentId()))
            .sorted(Comparator.comparingInt(SysDeptDB::getOrderNo))
            .map(dept -> {
                TreeNode<String> treeNode = new TreeNode();
                treeNode.setId(dept.getDeptId());
                treeNode.setParentId(dept.getParentId());
                treeNode.setName(dept.getDeptName());
                treeNode.setWeight(dept.getOrderNo());
                // 扩展属性
                Map<String, Object> extra = new HashMap<>(2);
                extra.put("createTime", dept.getCreateTime());
                treeNode.setExtra(extra);
                return treeNode;
            })
            .collect(Collectors.toList());

        return TreeUtil.build(collect, parentId);
    }

}
