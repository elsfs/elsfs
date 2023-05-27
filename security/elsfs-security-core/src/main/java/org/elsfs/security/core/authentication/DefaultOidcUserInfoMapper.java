package org.elsfs.security.core.authentication;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;

import java.util.*;

/**
 * @author zeng
 */
public class DefaultOidcUserInfoMapper implements InfoMapper<OidcUserInfoAuthenticationContext, OidcUserInfo> {

    private static final List<String> EMAIL_CLAIMS = Arrays.asList(StandardClaimNames.EMAIL,
            StandardClaimNames.EMAIL_VERIFIED);

    private static final List<String> PHONE_CLAIMS = Arrays.asList(StandardClaimNames.PHONE_NUMBER,
            StandardClaimNames.PHONE_NUMBER_VERIFIED);

    private static final List<String> PROFILE_CLAIMS = Arrays.asList(StandardClaimNames.NAME,
            StandardClaimNames.FAMILY_NAME, StandardClaimNames.GIVEN_NAME, StandardClaimNames.MIDDLE_NAME,
            StandardClaimNames.NICKNAME, StandardClaimNames.PREFERRED_USERNAME, StandardClaimNames.PROFILE,
            StandardClaimNames.PICTURE, StandardClaimNames.WEBSITE, StandardClaimNames.GENDER,
            StandardClaimNames.BIRTHDATE, StandardClaimNames.ZONEINFO, StandardClaimNames.LOCALE,
            StandardClaimNames.UPDATED_AT);

    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext authenticationContext) {
        var authorization = authenticationContext.getAuthorization();
        var idToken = authorization.getToken(OidcIdToken.class).getToken();
        var accessToken = authenticationContext.getAccessToken();
        var scopeRequestedClaims = getClaimsRequestedByScope(idToken.getClaims(), accessToken.getScopes());
        return new OidcUserInfo(scopeRequestedClaims);
    }

    private static Map<String, Object> getClaimsRequestedByScope(Map<String, Object> claims,
            Set<String> requestedScopes) {
        var scopeRequestedClaimNames = new HashSet<>(32);
        scopeRequestedClaimNames.add(StandardClaimNames.SUB);

        if (requestedScopes.contains(OidcScopes.ADDRESS)) {
            scopeRequestedClaimNames.add(StandardClaimNames.ADDRESS);
        }
        if (requestedScopes.contains(OidcScopes.EMAIL)) {
            scopeRequestedClaimNames.addAll(EMAIL_CLAIMS);
        }
        if (requestedScopes.contains(OidcScopes.PHONE)) {
            scopeRequestedClaimNames.addAll(PHONE_CLAIMS);
        }
        if (requestedScopes.contains(OidcScopes.PROFILE)) {
            scopeRequestedClaimNames.addAll(PROFILE_CLAIMS);
        }

        var requestedClaims = new HashMap<>(claims);
        requestedClaims.keySet().removeIf(claimName -> !scopeRequestedClaimNames.contains(claimName));

        return requestedClaims;
    }

}
