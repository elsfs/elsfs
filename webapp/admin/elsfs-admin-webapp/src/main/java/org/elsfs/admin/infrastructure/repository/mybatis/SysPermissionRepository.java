package org.elsfs.admin.infrastructure.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.elsfs.admin.infrastructure.dataobject.SysPermissionDB;

/**
 * @author zeng
 * @since 0.0.1
 */
@Mapper
public interface SysPermissionRepository extends BaseMapper<SysPermissionDB> {

}
