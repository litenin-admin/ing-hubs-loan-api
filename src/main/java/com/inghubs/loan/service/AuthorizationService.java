package com.inghubs.loan.service;

import com.inghubs.loan.config.security.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationService {

    public boolean isRequester(String guid) {
        var currentUser = getUserPrincipal();
        return StringUtils.equalsIgnoreCase(guid, currentUser.getGuid());
    }

    private UserPrincipal getUserPrincipal() {
        return ((UserPrincipal) getAuthentication().getPrincipal());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
