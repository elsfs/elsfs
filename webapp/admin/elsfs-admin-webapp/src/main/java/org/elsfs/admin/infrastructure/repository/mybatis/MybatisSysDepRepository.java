package org.elsfs.admin.infrastructure.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elsfs.admin.infrastructure.dataobject.SysDeptDB;
import org.elsfs.admin.infrastructure.repository.SysDepRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 系统部门仓储
 *
 * @author zeng
 * @since 0.0.1
 */
public interface MybatisSysDepRepository extends SysDepRepository, BaseMapper<SysDeptDB> {

    /**
     * 根据条件查询数据
     * @param sysDeptDB 查询条件
     * @return List<SysDeptDB>
     */
    @Override
    default List<SysDeptDB> findAll(SysDeptDB sysDeptDB) {
        return this.selectList(Wrappers.lambdaQuery(SysDeptDB.class)
            .likeRight(isNotBlank(sysDeptDB.getDeptName()), SysDeptDB::getDeptName, sysDeptDB.getDeptName())
            .eq(ObjectUtils.isNotEmpty(sysDeptDB.getStatus()), SysDeptDB::getStatus, sysDeptDB.getStatus()));
    }

    /**
     * 删除 deptId和子数据 TODO 删除下节点
     * @param id id
     * @return void
     */
    @Override
    default void cascadeDelete(String id) {
        SysDeptDB sysDeptDB = selectById(id);
        if (Objects.isNull(sysDeptDB) || Objects.isNull(sysDeptDB.getDeptId())) {
            return;
        }
        deleteById(sysDeptDB);
    }

    /**
     * @return
     */
    @Override
    default Optional<SysDeptDB> findById(String id) {
        return Optional.ofNullable(selectById(id));
    }

    /**
     * @return
     */
    @Override
    default List<SysDeptDB> findAll() {
        return selectList(Wrappers.emptyWrapper());
    }

    /**
     * 如果根据id查询 如果存在则更新，如果不存在则新增
     * @param sysDeptDB sysDeptDB
     */
    @Override
    default void save(SysDeptDB sysDeptDB) {
        if (StringUtils.isNotBlank(sysDeptDB.getDeptId())) {
            SysDeptDB deptDB = selectById(sysDeptDB);
            if (deptDB != null) {
                updateById(sysDeptDB);
                return;
            }
        }
        insert(sysDeptDB);

    }

}
