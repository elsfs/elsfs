package org.elsfs.admin.adapter;

import lombok.RequiredArgsConstructor;
import org.elsfs.admin.client.api.SysDeptService;
import org.elsfs.admin.client.dto.DeptQry;
import org.elsfs.admin.client.dto.clientobject.dept.DeptCO;
import org.elsfs.admin.client.dto.clientobject.dept.DeptTreeCO;
import org.elsfs.core.dto.MultiResponse;
import org.elsfs.core.dto.Response;
import org.elsfs.core.dto.SingleResponse;
import org.elsfs.core.util.lang.tree.Tree;
import org.springframework.web.bind.annotation.*;

/**
 * 部门控制器
 *
 * @author zeng
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dept")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 通过ID查询
     * @param id ID
     * @return DeptCO
     */
    @GetMapping("/{id:\\d+}")
    public SingleResponse<DeptCO> getById(@PathVariable String id) {
        return sysDeptService.getById(id);
    }

    /**
     * 返回树形菜单集合
     * @return 全部数据树形菜单
     */
    @GetMapping(value = "/tree")
    public MultiResponse<Tree<String>> listDeptTrees() {
        return sysDeptService.listDeptTrees();
    }

    @GetMapping("getDeptList")
    public MultiResponse<DeptTreeCO> getBatch(DeptQry deptQry) {
        return sysDeptService.getDeptList(deptQry, true);
    }

    @PutMapping
    public Response update(@RequestBody DeptCO deptCO) {
        return sysDeptService.updateById(deptCO);
    }

    @PostMapping
    public Response save(@RequestBody DeptCO deptCO) {
        return sysDeptService.save(deptCO);
    }

    @DeleteMapping("/{deptId}")
    public Response deleteById(@PathVariable String deptId) {
        return sysDeptService.delete(deptId);
    }

}
