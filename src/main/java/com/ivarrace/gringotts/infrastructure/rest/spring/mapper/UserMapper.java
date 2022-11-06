package com.ivarrace.gringotts.infrastructure.rest.spring.mapper;

import com.ivarrace.gringotts.domain.user.User;
import com.ivarrace.gringotts.domain.user.UserAuthority;
import com.ivarrace.gringotts.infrastructure.db.springdata.mapper.Utils;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewUserCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.UserResponse;

public class UserMapper {

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

   public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setId(Utils.toUUID(user.getId()));
        userResponse.setCreatedDate(user.getCreatedDate());
        userResponse.setLastModified(user.getLastModified());
        userResponse.setUsername(user.getUsername());
        userResponse.setAuthority(user.getAuthority().name());
        return userResponse;
    }

    public static User toDomain(NewUserCommand newUser) {
        if (newUser == null) {
            return null;
        }
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setAuthority(UserAuthority.USER);
        return user;
    }
}