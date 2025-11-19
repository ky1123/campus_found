// UserController.java
package com.zhixun.demo.controller;

import com.zhixun.demo.Service.UserService;
import com.zhixun.demo.dto.response.ApiResponse;
import com.zhixun.demo.dto.request.RegisterRequest;
import com.zhixun.demo.dto.request.LoginRequest;
import com.zhixun.demo.dto.request.UpdateUserRequest;
import com.zhixun.demo.dto.response.UserResponse;
import com.zhixun.demo.Entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ApiResponse.success("注册成功", convertToResponse(user));
    }

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request);
        return ApiResponse.success("登录成功", convertToResponse(user));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ApiResponse.success(convertToResponse(user));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(id, request);
        return ApiResponse.success("更新成功", convertToResponse(user));
    }

    @GetMapping("/check-phone")
    public ApiResponse<Boolean> checkPhoneNumber(@RequestParam String phoneNumber) {
        boolean available = userService.isPhoneNumberAvailable(phoneNumber);
        String message = available ? "手机号可用" : "手机号已被注册";
        return ApiResponse.success(message, available);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setOtherContact(user.getOtherContact());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}