package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.UserService;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.NewUserCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.UserResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid NewUserCommand request) {
        UserResponse response = UserMapper.INSTANCE.toResponse(userService.save(UserMapper.INSTANCE.toDomain(request)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
