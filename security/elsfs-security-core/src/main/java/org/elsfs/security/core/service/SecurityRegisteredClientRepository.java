package org.elsfs.security.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * 注册的客户端
 *
 * @author zeng
 * @since 0.0.1
 */

public final class SecurityRegisteredClientRepository implements RegisteredClientRepository {

    private final SecurityRegisteredClientService securityRegisteredClientService;

    public SecurityRegisteredClientRepository(SecurityRegisteredClientService securityRegisteredClientService) {
        this.securityRegisteredClientService = securityRegisteredClientService;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        try {
            securityRegisteredClientService.save(RegisteredClientConvert.toServiceObject(registeredClient));
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        try {
            return RegisteredClientConvert.toServiceObject(securityRegisteredClientService.findById(id));
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        try {
            return RegisteredClientConvert.toServiceObject(securityRegisteredClientService.findByClientId(clientId));
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
