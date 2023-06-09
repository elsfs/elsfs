package org.elsfs.admin.app.service;

import lombok.RequiredArgsConstructor;
import org.elsfs.admin.app.command.dept.DeptDeleteCmdExe;
import org.elsfs.admin.app.command.dept.DeptUpdateByIdOrSaveCmdExe;
import org.elsfs.admin.app.command.query.DeptByIdQryExe;
import org.elsfs.admin.app.command.query.DeptListDeptTreesQryExe;
import org.elsfs.admin.app.command.query.DeptListQryExe;
import org.elsfs.admin.client.api.SysDeptService;
import org.elsfs.admin.client.dto.DeptQry;
import org.elsfs.admin.client.dto.clientobject.dept.DeptCO;
import org.elsfs.admin.client.dto.clientobject.dept.DeptTreeCO;
import org.elsfs.core.dto.MultiResponse;
import org.elsfs.core.dto.Response;
import org.elsfs.core.dto.SingleResponse;
import org.elsfs.core.util.lang.tree.Tree;
import org.springframework.stereotype.Service;

/**
 * @author zeng
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl implements SysDeptService {

    private final DeptListQryExe deptListQryExe;

    private final DeptDeleteCmdExe deptDeleteCmdExe;

    private final DeptByIdQryExe deptByIdQryExe;

    private final DeptListDeptTreesQryExe deptListDeptTreesQryExe;

    private final DeptUpdateByIdOrSaveCmdExe deptUpdateByIdOrSaveCmdExe;

    @Override
    public MultiResponse<DeptTreeCO> getDeptList(DeptQry deptQry, Boolean isTree) {
        return deptListQryExe.execute(deptQry, isTree);
    }

    @Override
    public Response delete(String deptId) {
        return deptDeleteCmdExe.execute(deptId);
    }

    @Override
    public SingleResponse<DeptCO> getById(String id) {
        return deptByIdQryExe.execute(id);
    }


    @Override
    public MultiResponse<Tree<String>> listDeptTrees() {
        return deptListDeptTreesQryExe.execute();
    }


    @Override
    public Response updateById(DeptCO deptDB) {
        return deptUpdateByIdOrSaveCmdExe.execute(deptDB);
    }

    /**
     * @param deptCO deptCO
     * @return Response
     */
    @Override
    public Response save(DeptCO deptCO) {
        return deptUpdateByIdOrSaveCmdExe.execute(deptCO);
    }

}
