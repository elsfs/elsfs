package org.elsfs.security.core.jwt;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.context.Context;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.util.Assert;

import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author zeng
 */
public interface JutTokenContext extends Context {

    default AuthorizationServerSettings getAuthorizationServerSettings() {
        return get(AuthorizationServerSettings.class);
    }

    default TemporalAmount getTimeToLive() {
        return get(TemporalAmount.class);
    }

    default HttpSession getHttpSession() {
        return get(HttpSession.class);
    }

    default JwsHeader.Builder getJwsHeader() {
        return get(JwsHeader.Builder.class);
    }

    default JwtClaimsSet.Builder getClaims() {
        return get(JwtClaimsSet.Builder.class);
    }

    @SuppressWarnings({ "unchecked" })
    default <T extends UserDetails> T getUserDetails() {
        return (T) get(UserDetails.class);
    }

    abstract class AbstractBuilder<T extends JutTokenContext, B extends AbstractBuilder<T, B>> {

        private final Map<Object, Object> context = new HashMap<>();

        public <M extends UserDetails> B userDetails(M userDetails) {
            return put(UserDetails.class, userDetails);
        }

        public B authorizationServerSettings(AuthorizationServerSettings authorizationServerSettings) {
            return put(AuthorizationServerSettings.class, authorizationServerSettings);
        }

        public B httpSession(HttpSession httpSession) {
            return put(HttpSession.class, httpSession);
        }

        // 令牌有效期
        public B timeToLive(TemporalAmount temporalAmount) {
            return put(TemporalAmount.class, temporalAmount);
        }

        public B put(Object key, Object value) {
            Assert.notNull(key, "key cannot be null");
            Assert.notNull(value, "value cannot be null");
            this.context.put(key, value);
            return getThis();
        }

        public B context(Consumer<Map<Object, Object>> contextConsumer) {
            contextConsumer.accept(this.context);
            return getThis();
        }

        @SuppressWarnings("unchecked")
        protected <V> V get(Object key) {
            return (V) this.context.get(key);
        }

        protected Map<Object, Object> getContext() {
            return this.context;
        }

        @SuppressWarnings("unchecked")
        protected final B getThis() {
            return (B) this;
        }

        public abstract T build();

    }

}
