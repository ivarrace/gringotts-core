package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.service.UserService;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.command.NewUserCommand;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.response.UserResponse;
import com.ivarrace.gringotts.infrastructure.rest.spring.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Hidden
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid NewUserCommand request) {
        UserResponse response = UserMapper.INSTANCE.toResponse(userService.save(UserMapper.INSTANCE.toDomain(request)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
