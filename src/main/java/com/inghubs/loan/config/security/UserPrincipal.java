package com.inghubs.loan.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class UserPrincipal extends User {

    private final String guid;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities, String guid) {
        super(username, password, authorities);
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

}
