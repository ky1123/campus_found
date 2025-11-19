// UserService.java
package com.zhixun.demo.Service;

import com.zhixun.demo.Entity.User;
import com.zhixun.demo.dto.request.RegisterRequest;
import com.zhixun.demo.dto.request.LoginRequest;
import com.zhixun.demo.dto.request.UpdateUserRequest;

public interface UserService {

    User register(RegisterRequest request);

    User login(LoginRequest request);

    User getUserById(Long id);

    User updateUser(Long userId, UpdateUserRequest request);

    void changePassword(Long userId, String oldPassword, String newPassword);

    boolean isPhoneNumberAvailable(String phoneNumber);
}