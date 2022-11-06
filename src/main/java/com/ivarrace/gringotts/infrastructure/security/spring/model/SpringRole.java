package com.ivarrace.gringotts.infrastructure.security.spring.model;

import org.springframework.security.core.GrantedAuthority;

public class SpringRole implements GrantedAuthority {

    public static final SpringRole USER = new SpringRole("USER");
    public static final SpringRole ADMIN = new SpringRole("ADMIN");

    private String authority;

    public SpringRole(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}