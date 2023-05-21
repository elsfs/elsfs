package org.elsfs.security.core.service;

import org.elsfs.security.core.entity.SecurityRegisteredClient;

/**
 * 注册的客户端
 *
 * @author zeng
 * @since 0.0.1
 */
public interface SecurityRegisteredClientService {

    void save(SecurityRegisteredClient registeredClient);

    SecurityRegisteredClient findById(String id);

    SecurityRegisteredClient findByClientId(String clientId);

}
