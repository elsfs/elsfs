package org.elsfs.admin.infrastructure.repository;

import org.elsfs.security.core.userdetails.SecurityUser;

/**
 * @author zeng
 * @since 0.0.1
 */
public interface SecurityUserCacheRepository {

    SecurityUser getUserFromCache(String username);

    void putUserInCache(SecurityUser user);

    void removeUserFromCache(String username);

}
