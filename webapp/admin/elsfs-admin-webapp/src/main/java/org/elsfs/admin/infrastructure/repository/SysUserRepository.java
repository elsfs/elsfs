package org.elsfs.admin.infrastructure.repository;

import org.elsfs.admin.infrastructure.dataobject.SysUserDB;

import java.util.Optional;

/**
 * 控制台用户
 *
 * @author zeng
 * @since 0.0.1
 */

public interface SysUserRepository {

    Optional<SysUserDB> findByUsername(String username);

    Optional<SysUserDB> findByPhone(String phone);

    Optional<SysUserDB> findByEmail(String email);

    Optional<SysUserDB> findByUserId(String userId);

}
