package com.ivarrace.gringotts.domain.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {

    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private String username;
    private String password;
    private UserAuthority authority;
    private boolean enabled = true;
    private boolean nonExpired = true;
    private boolean nonLocked = true;
    private boolean credentialNonExpired = true;

}
