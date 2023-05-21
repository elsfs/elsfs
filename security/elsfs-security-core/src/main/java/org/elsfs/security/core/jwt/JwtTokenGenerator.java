package org.elsfs.security.core.jwt;

import org.springframework.security.oauth2.jwt.JwtClaimAccessor;

/**
 * @author zeng
 */
@FunctionalInterface
public interface JwtTokenGenerator<T extends JwtClaimAccessor> extends JwtGeneratorFactory<T, JutTokenContext> {

    T generate(JutTokenContext context);

}
