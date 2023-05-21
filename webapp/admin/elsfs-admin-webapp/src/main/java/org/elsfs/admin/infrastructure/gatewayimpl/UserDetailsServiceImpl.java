package org.elsfs.admin.infrastructure.gatewayimpl;

import lombok.RequiredArgsConstructor;
import org.elsfs.admin.infrastructure.dataobject.SysUserDB;
import org.elsfs.admin.infrastructure.repository.SecurityUserCacheRepository;
import org.elsfs.admin.infrastructure.repository.SysUserRepository;
import org.elsfs.security.core.userdetails.SecurityUser;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author zeng
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserRepository sysUserRepository;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private final SecurityUserCacheRepository securityUserCacheRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = securityUserCacheRepository.getUserFromCache(username);
        if (user == null) {
            SysUserDB userDB = sysUserRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials")));
            user = LoginGatewayImpl.createSecurityUser(userDB, AuthorityUtils.createAuthorityList("sss", "user"));
        }
        securityUserCacheRepository.putUserInCache(user);
        return user;
    }

}
