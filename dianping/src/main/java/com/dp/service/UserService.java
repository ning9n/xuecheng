package com.dp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dp.dto.LoginFormDTO;
import com.dp.dto.Result;
import com.dp.dto.UserDTO;
import com.dp.entity.User;

import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {
    void sendCode(String phone, HttpSession session);

    Result checkAndLogin(LoginFormDTO loginForm, HttpSession session);

    UserDTO me();

    User info(Long userId);

    Result signCount();

    Result sign();
}
