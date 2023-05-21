package org.elsfs.security.core.util;

import org.apache.commons.lang3.StringUtils;
import org.elsfs.security.core.userdetails.SecurityUser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import java.util.List;

/**
 * @author zeng
 * @since 0.0.1
 */
@Component
public class SecurityUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Lazy
    private static UserDetailsService getUserDetailsService() {
        return getApplicationContext().getBean(UserDetailsService.class);
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Jwt getJwt() {
        return getAuthentication().getPrincipal() instanceof Jwt jwt ? jwt : null;
    }

    /**
     * 获取用户
     */
    public static UserDetails getUserDetails() {
        return getAuthentication().getPrincipal() instanceof UserDetails securityUser ? securityUser : null;
    }

    public static SecurityUser getUser() {
        SecurityUser securityUser = null;
        Jwt jwt = getJwt();
        if (jwt == null) {
            UserDetails details = getUserDetails();
            if (details != null) {
                if (details instanceof SecurityUser user) {
                    securityUser = user;
                }
            }
        }
        try {
            if (securityUser == null && jwt != null) {
                String subject = jwt.getSubject();
                if (subject != null) {
                    securityUser = (SecurityUser) getUserDetailsService().loadUserByUsername(subject);
                }
            }
            if (securityUser == null) {
                throw new UsernameNotFoundException("user not ");
            }
            return securityUser;
        }
        catch (UsernameNotFoundException e) {
            throw new TypeNotPresentException("org.elsfs.security.core.userdetails.SecurityUser", null);
        }

    }

    public static String getUserId() {
        return getUser().getUserId();
    }

    public static String getUsername() {
        if (getJwt() != null) {
            return getJwt().getSubject();
        }
        return getUser().getUsername();
    }

    /**
     * 获取用户角色信息
     * @return 角色集合
     */
    public static List<String> getRoles() {
        Authentication authentication = getAuthentication();
        var authorities = authentication.getAuthorities();

        List<String> roleIds = new ArrayList<>();
        authorities.stream()
            .filter(granted -> StringUtils.startsWith(granted.getAuthority(), "ROLE_"))
            .forEach(granted -> {
                String id = StringUtils.removeStart(granted.getAuthority(), "ROLE_");
                roleIds.add(id);
            });
        return roleIds;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
