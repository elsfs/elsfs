package org.elsfs.admin.infrastructure.repository;

import org.elsfs.admin.infrastructure.dataobject.SysDeptDB;

import java.util.List;
import java.util.Optional;

/**
 * 系统部门仓储
 *
 * @author zeng
 * @since 0.0.1
 */

public interface SysDepRepository {

    /**
     * 根据条件查询数据
     * @param sysDeptDB 查询条件
     * @return List<SysDeptDB>
     */
    List<SysDeptDB> findAll(SysDeptDB sysDeptDB);

    List<SysDeptDB> findAll();

    /**
     * 删除 deptId和子数据
     * @param id id
     * @return void
     */
    void cascadeDelete(String id);

    Optional<SysDeptDB> findById(String id);

}
