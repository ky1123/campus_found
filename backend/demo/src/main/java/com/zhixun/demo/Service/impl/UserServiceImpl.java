package com.zhixun.demo.Service.impl;

import com.zhixun.demo.Entity.User;
import com.zhixun.demo.Repository.UserRepository;
import com.zhixun.demo.Service.UserService;
import com.zhixun.demo.dto.request.RegisterRequest;
import com.zhixun.demo.dto.request.LoginRequest;
import com.zhixun.demo.dto.request.UpdateUserRequest;
import com.zhixun.demo.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequest request) {
        // 验证手机号是否已存在
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomException("手机号已被注册");
        }

        // 创建用户
        User user = new User();
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setOtherContact(request.getOtherContact());

        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequest request) {
        User user = userRepository.findByPhoneNumber(request.getName())
                .orElseThrow(() -> new CustomException("用户不存在"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("密码错误");
        }

        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("用户不存在"));
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequest request) {
        User user = getUserById(userId);
        user.setName(request.getName());
        user.setOtherContact(request.getOtherContact());
        return userRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new CustomException("原密码错误");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean isPhoneNumberAvailable(String phoneNumber) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }
}