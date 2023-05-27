package org.elsfs.admin.infrastructure.repository.noop;

import org.elsfs.admin.infrastructure.repository.SecurityUserCacheRepository;
import org.elsfs.security.core.userdetails.SecurityUser;

/**
 * @author zeng
 * @since 0.0.1
 */
public class NoopSecurityUserCacheRepository implements SecurityUserCacheRepository {

    /**
     * @param username
     * @return
     */
    @Override
    public SecurityUser getUserFromCache(String username) {
        return null;
    }

    /**
     * @param user
     */
    @Override
    public void putUserInCache(SecurityUser user) {

    }

    /**
     * @param username
     */
    @Override
    public void removeUserFromCache(String username) {

    }

}
