package org.elsfs.security.core.service;

import org.elsfs.security.core.exception.ThirdPartyException;
import org.elsfs.security.core.userdetails.SecurityUser;
import org.elsfs.security.core.userdetails.ThirdPartyUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SecurityUserDetailsService extends UserDetailsService, ThirdPartyUserService {

    @Override
    default SecurityUser load(ThirdPartyUserDetails thirdPartyUserDetails, Filter filter) throws ThirdPartyException {
        try {
            SecurityUser securityUser = loadUserByThirdPartyUserId(thirdPartyUserDetails, filter);
            return securityUser;
        }
        catch (UsernameNotFoundException e) {
            throw new ThirdPartyException(e.getLocalizedMessage());
        }
    }

    @Override
    SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException;

    SecurityUser loadUserByThirdPartyUserId(ThirdPartyUserDetails thirdPartyUserDetails, Filter filter)
            throws UsernameNotFoundException, ThirdPartyException;

}
