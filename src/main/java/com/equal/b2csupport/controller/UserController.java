package com.equal.b2csupport.controller;

import com.equal.b2csupport.dto.user.UserResponse;
import com.equal.b2csupport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("info")
    public ResponseEntity<UserResponse> getUserInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }
}
