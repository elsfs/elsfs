package org.elsfs.security.core.jwt.resource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * 资源模式下转换为其他类型的 AbstractAuthenticationToken
 * <p>
 * spring security resource server 默认通过 通过
 * {@link JwtAuthenticationProvider#authenticate(Authentication)} 方法 转换为
 * {@link JwtAuthenticationConverter} 来输出 {@link JwtAuthenticationToken},
 * <p>
 * 使用提供此类，转换为不同的 AbstractAuthenticationToken 在不同的应用更好的获取认证的用户信息
 *
 * @see JwtAuthenticationConverter
 * @see JwtAuthenticationToken
 * @see JwtAuthenticationProvider
 * @author zeng
 * @since 0.0.1
 */

public class JwtAuthenticationConvertersAdapter<T extends AbstractAuthenticationToken> implements Converter<Jwt, T> {

    private final Converter<Jwt, T> JwtAuthenticationConverter;

    public JwtAuthenticationConvertersAdapter(Converter<Jwt, T> jwtAuthenticationConverter) {
        JwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    /**
     * @param jwt
     * @return
     */
    @Override
    public T convert(Jwt jwt) {
        return JwtAuthenticationConverter.convert(jwt);
    }

}
