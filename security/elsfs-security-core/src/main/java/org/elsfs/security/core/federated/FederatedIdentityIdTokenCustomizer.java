/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elsfs.security.core.federated;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.*;

/**
 * OAuth2Token自定义程序 将 {@link OAuth2TokenCustomizer}声明从联合身份映射到 这个 {@code id_token}
 * 由该授权服务器生成。
 */
public final class FederatedIdentityIdTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private static final Set<String> ID_TOKEN_CLAIMS = Collections
        .unmodifiableSet(new HashSet<>(Arrays.asList(IdTokenClaimNames.ISS, IdTokenClaimNames.SUB,
                IdTokenClaimNames.AUD, IdTokenClaimNames.EXP, IdTokenClaimNames.IAT, IdTokenClaimNames.AUTH_TIME,
                IdTokenClaimNames.NONCE, IdTokenClaimNames.ACR, IdTokenClaimNames.AMR, IdTokenClaimNames.AZP,
                IdTokenClaimNames.AT_HASH, IdTokenClaimNames.C_HASH)));

    @Override
    public void customize(JwtEncodingContext context) {
        if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
            var thirdPartyClaims = extractClaims(context.getPrincipal());
            context.getClaims().claims(existingClaims -> {
                // 删除此授权服务器设置的冲突声明
                existingClaims.keySet().forEach(thirdPartyClaims::remove);
                // 删除可能导致客户端问题的标准id_token声明
                ID_TOKEN_CLAIMS.forEach(thirdPartyClaims::remove);
                // 将所有其他声明直接添加到id_token
                existingClaims.putAll(thirdPartyClaims);
            });
        }
    }

    private Map<String, Object> extractClaims(Authentication principal) {
        Map<String, Object> claims;
        if (principal.getPrincipal() instanceof OidcUser oidcUser) {
            var idToken = oidcUser.getIdToken();
            claims = idToken.getClaims();
        }
        else if (principal.getPrincipal() instanceof OAuth2User oauth2User) {
            claims = oauth2User.getAttributes();
        }
        else {
            claims = Collections.emptyMap();
        }

        return new HashMap<>(claims);
    }

}
