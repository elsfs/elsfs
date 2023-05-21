package org.elsfs.admin.infrastructure.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.elsfs.admin.infrastructure.dataobject.SysUserDB;
import org.elsfs.admin.infrastructure.repository.SysUserRepository;

import java.util.Optional;

/**
 * 控制台用户
 *
 * @author zeng
 * @since 0.0.1
 */
@Mapper
public interface MybatisSysUserRepository extends SysUserRepository, BaseMapper<SysUserDB> {

    @Override
    default Optional<SysUserDB> findByUsername(String username) {
        return Optional
            .ofNullable(selectOne(Wrappers.lambdaQuery(SysUserDB.class).eq(SysUserDB::getUsername, username)));
    }

    @Override
    default Optional<SysUserDB> findByPhone(String phone) {
        return Optional.ofNullable(selectOne(Wrappers.lambdaQuery(SysUserDB.class).eq(SysUserDB::getPhone, phone)));
    }

    @Override
    default Optional<SysUserDB> findByEmail(String email) {
        return Optional.ofNullable(selectOne(Wrappers.lambdaQuery(SysUserDB.class).eq(SysUserDB::getEmail, email)));
    }

    @Override
    default Optional<SysUserDB> findByUserId(String userId) {
        return Optional.ofNullable(selectOne(Wrappers.lambdaQuery(SysUserDB.class).eq(SysUserDB::getUserId, userId)));
    }

}
