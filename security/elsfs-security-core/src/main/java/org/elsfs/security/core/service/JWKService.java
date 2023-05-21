package org.elsfs.security.core.service;

import com.nimbusds.jose.jwk.*;

import java.util.List;

/**
 * jwks 配置接口
 *
 * @author zeng
 */
@FunctionalInterface
public interface JWKService {

    List<JWK> getJWTs();

}
