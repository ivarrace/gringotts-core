package com.ivarrace.gringotts.domain.user;

import com.ivarrace.gringotts.application.exception.InvalidParameterException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public UUID getUUID(){
        try {
            return UUID.fromString(this.id);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidParameterException("id");
        }
    }

}
