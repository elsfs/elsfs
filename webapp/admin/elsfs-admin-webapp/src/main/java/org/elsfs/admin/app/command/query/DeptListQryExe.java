package org.elsfs.admin.app.command.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elsfs.admin.client.dto.DeptQry;
import org.elsfs.admin.client.dto.clientobject.dept.DeptTreeCO;
import org.elsfs.admin.infrastructure.dataobject.SysDeptDB;
import org.elsfs.admin.infrastructure.repository.SysDepRepository;
import org.elsfs.core.dto.MultiResponse;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 查询部门列表
 *
 * @author zeng
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeptListQryExe {

    private final SysDepRepository sysDepRepository;

    /**
     * 查询部门列表
     * @param deptQry 查询参数
     * @param isTree 是否转换为树 true 转换树 false 平铺
     * @return 部门列表信息
     */
    public MultiResponse<DeptTreeCO> execute(DeptQry deptQry, Boolean isTree) {
        long start = System.currentTimeMillis();
        SysDeptDB sysDeptDB = new SysDeptDB();
        BeanUtils.copyProperties(deptQry, sysDeptDB);
        List<SysDeptDB> list = sysDepRepository.findAll(sysDeptDB);
        if (CollectionUtils.isEmpty(list)) {
            return MultiResponse.of(new ArrayList<>());
        }
        List<DeptTreeCO> deptList = convert(list);
        if (!isTree) {
            return MultiResponse.of(deptList);
        }
        String parentId = deptQry.getParentId() == null ? "0" : deptQry.getParentId();
        List<DeptTreeCO> tree = new TreeConvert().buildTree(deptList, parentId);
        long end = System.currentTimeMillis();
        LOGGER.debug("构建部门树时间：{}", end - start);
        return MultiResponse.of(tree);
    }

    /**
     * do转 co
     * @param list
     * @return
     */
    private List<DeptTreeCO> convert(List<SysDeptDB> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        synchronized (list) {
            ArrayList<DeptTreeCO> arrayList = new ArrayList<>();
            for (SysDeptDB deptDB : list) {
                DeptTreeCO treeCO = new DeptTreeCO();
                BeanUtils.copyProperties(deptDB, treeCO);
                arrayList.add(treeCO);
            }
            return arrayList;
        }
    }

    /**
     * @author zeng
     * @since 0.0.1
     */
    private class TreeConvert {

        // 原来的数据
        private HashMap<String, List<DeptTreeCO>> original;

        private List<DeptTreeCO> parentList = new ArrayList<>();

        private TreeConvert() {
        }

        private List<DeptTreeCO> buildTree(List<DeptTreeCO> data, String parentId) {
            if (CollectionUtils.isEmpty(data)) {
                return new ArrayList<>();
            }
            init(data);
            List<DeptTreeCO> toList = original.get(parentId);
            parentList.addAll(toList);
            handle(parentList);
            return parentList;
        }

        void handle(List<DeptTreeCO> list) {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            list.stream().sorted(Comparator.comparing(DeptTreeCO::getOrderNo)).forEach(co -> {
                List<DeptTreeCO> treeCOSet = original.get(co.getDeptId());
                if (!CollectionUtils.isEmpty(treeCOSet)) {
                    List<DeptTreeCO> toList = treeCOSet.stream().toList();
                    co.setChildren(toList);
                    handle(toList);
                }
            });
        }

        void init(List<DeptTreeCO> deptTreeCOS) {
            this.original = new HashMap<>();
            for (DeptTreeCO co : deptTreeCOS) {
                if (!this.original.containsKey(co.getParentId())) {
                    List<DeptTreeCO> set = new ArrayList<>();
                    set.add(co);
                    this.original.put(co.getParentId(), set);
                }
                else {
                    this.original.get(co.getParentId()).add(co);
                }
            }
        }

    }

}
