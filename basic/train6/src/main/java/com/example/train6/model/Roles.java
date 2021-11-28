package com.example.train6.model;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {

    ROLE_MANAGER,
    ROLE_ADMIN,
    ROLE_EMPLOYEE,
    ;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
