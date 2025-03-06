package com.dp.controller;


import com.dp.dto.LoginFormDTO;
import com.dp.dto.Result;
import com.dp.dto.UserDTO;
import com.dp.entity.User;
import com.dp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;



    /**
     * 发送手机验证码
     */
    @PostMapping("code")
    public Result<?> sendCode(@RequestParam("phone") String phone, HttpSession session) {
        // 发送短信验证码并保存验证码
        userService.sendCode(phone,session);
        return Result.success();
    }

    /**
     * 登录功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginFormDTO loginForm, HttpSession session){
        // 实现登录功能
        return userService.checkAndLogin(loginForm, session);
    }

    /**
     * 登出功能
     * @return 无
     */
    @PostMapping("/logout")
    public Result logout(){
        // TODO 实现登出功能
        return Result.fail("功能未完成");
    }

    /**
     * 获取当前登录的用户信息。
     *
     * @return 包含当前登录用户信息的结果对象。
     */
    @GetMapping("/me")
    public Result<UserDTO> me() {
        UserDTO dto=userService.me();
        return Result.success(dto);
    }

    /**
     * 根据用户ID获取用户详细信息。
     *
     * @param userId 用户的唯一标识符。
     * @return 包含指定用户详细信息的结果对象。
     */
    @GetMapping("/info/{id}")
    public Result<?> info(@PathVariable("id") Long userId) {
        User user=userService.info(userId);
        return Result.success(user);
    }

    /**
     * 根据用户ID查询用户信息。
     *
     * @param userId 用户的唯一标识符。
     * @return 包含指定用户信息的结果对象。
     */
    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable("id") Long userId) {
        return null;
    }

    /**
     * 用户签到操作。
     *
     * @return 包含签到结果的信息对象。
     */
    @PostMapping("/sign")
    public Result sign() {
        return userService.sign();
    }

    /**
     * 获取用户的签到统计信息。
     *
     * @return 包含签到统计信息的结果对象。
     */
    @GetMapping("/sign/count")
    public Result signCount() {
        return userService.signCount();
    }
}