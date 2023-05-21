package org.elsfs.admin.client.api;

import org.elsfs.admin.client.dto.DeptQry;
import org.elsfs.admin.client.dto.clientobject.dept.DeptCO;
import org.elsfs.admin.client.dto.clientobject.dept.DeptTreeCO;
import org.elsfs.admin.infrastructure.dataobject.SysDeptDB;
import org.elsfs.core.dto.MultiResponse;
import org.elsfs.core.dto.Response;
import org.elsfs.core.dto.SingleResponse;
import org.elsfs.core.util.lang.tree.Tree;

/**
 * 系统登陆相关接口
 *
 * @author zeng
 * @serial 0.0.1
 */

public interface SysDeptService {

    /**
     * 查询部门列表
     * @param deptQry 查询参数
     * @param isTree 是否转换为树 true 转换树 false 平铺
     * @return 部门列表信息
     */
    MultiResponse<DeptTreeCO> getDeptList(DeptQry deptQry, Boolean isTree);

    /**
     * 删除
     * @param deptId 部门id
     * @return
     */
    Response delete(String deptId);

    /**
     * 根据id 查询
     * @param id id
     * @return SingleResponse
     */
    SingleResponse<DeptCO> getById(String id);

    MultiResponse<Tree<String>> listDeptTrees();

    Response updateById(SysDeptDB deptDB);

    Response save(DeptCO deptCO);

}
