package org.elsfs.security.core.jwt;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zeng
 */
public class JutTokenContextImpl implements JutTokenContext {

    private final Map<Object, Object> context;

    private JutTokenContextImpl(Map<Object, Object> context) {
        this.context = Collections.unmodifiableMap(new HashMap<>(context));
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <V> V get(Object key) {
        return hasKey(key) ? (V) this.context.get(key) : null;
    }

    @Override
    public boolean hasKey(Object key) {
        Assert.notNull(key, "key cannot be null");
        return this.context.containsKey(key);
    }

    public static Builder with(JwsHeader.Builder jwsHeaderBuilder, JwtClaimsSet.Builder claimsBuilder) {
        return new Builder(jwsHeaderBuilder, claimsBuilder);
    }

    public static final class Builder extends AbstractBuilder<JutTokenContextImpl, Builder> {

        private Builder(JwsHeader.Builder jwsHeaderBuilder, JwtClaimsSet.Builder claimsBuilder) {
            Assert.notNull(jwsHeaderBuilder, "jwsHeaderBuilder cannot be null");
            Assert.notNull(claimsBuilder, "claimsBuilder cannot be null");
            put(JwsHeader.Builder.class, jwsHeaderBuilder);
            put(JwtClaimsSet.Builder.class, claimsBuilder);
        }

        public JutTokenContextImpl build() {
            return new JutTokenContextImpl(getContext());
        }

    }

}
