package com.dp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dp.dto.LoginFormDTO;
import com.dp.dto.Result;
import com.dp.dto.UserDTO;
import com.dp.entity.User;
import com.dp.mapper.UserMapper;
import com.dp.service.UserService;
import com.dp.util.RedisConstants;
import com.dp.util.UserHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final StringRedisTemplate template;
    private final UserMapper userMapper;

    /**
     * 生成6位验证码
     */
    @Override
    public void sendCode(String phone, HttpSession session) {
        //生成验证码
        int randomInt = RandomUtil.randomInt(100000, 999999);
        String code = String.valueOf(randomInt);
        //保存到redis，并设置有效期
        String key = RedisConstants.CODE + phone;
        template.opsForValue().set(key, code, RedisConstants.CODE_TTL, RedisConstants.CODE_TIMEUNIT);

        //日志记录替代发送验证码‘
        log.info("手机：{}的验证码：{}", phone, code);
    }

    /**
     * 分为密码登陆和验证码登录
     *
     * @param loginForm 用户信息
     * @param session   session,，用于写token
     * @return 结果
     */
    @Override
    public Result<?> checkAndLogin(LoginFormDTO loginForm, HttpSession session) {
        //判断用户是否存在
        String phone = loginForm.getPhone();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
        if (user == null) {
            throw new NoSuchElementException("用户不存在");
        }
        //验证码登录
        String code = loginForm.getCode();

        if (code != null && !code.isEmpty()) {
            String string = template.opsForValue().get(RedisConstants.CODE + phone);
            if (code.equals(string)) {
                login(user, phone, session, "验证码");
            }
            log.info("用户验证码输入错误，手机号：{}", phone);
            return Result.fail("验证码错误");
        }
        //密码登录
        String password = loginForm.getPassword();
        if (password.equals(user.getPassword())) {
            login(user, phone, session, "密码");
        }
        log.info("用户密码输入错误，手机号：{}", phone);
        return Result.fail("验证码错误");

    }

    /**
     * 生成token/保存到session/保存到redis
     * @param user
     * @param phone
     * @param session
     * @param passwordOrCode
     */
    private void login(User user, String phone, HttpSession session, String passwordOrCode) {
        String token = getToken(phone);
        session.setAttribute(RedisConstants.USER_LOGIN_TOKEN, token);
        UserDTO dto = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> map = BeanUtil.beanToMap(dto, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((name, value) -> value.toString()));
        String key = RedisConstants.USER_LOGIN_TOKEN + token;
        template.opsForHash().putAll(key,map);
        template.expire(key,RedisConstants.USER_LOGIN_TOKEN_TTL,RedisConstants.USER_LOGIN_TOKEN_TIMEUNIT);
        log.info("用户{}登录成功，手机号：{},token:{}", passwordOrCode, phone, token);
    }

    @Override
    public UserDTO me() {
        return UserHolder.getUser();
    }

    @Override
    public User info(Long userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 判断用户本月连续签到天数
     *
     * @return
     */

    @Override
    public Result signCount() {
        //获取用户信息
        Long userId = UserHolder.getUser().getId();
        //获取日期
        LocalDateTime now = LocalDateTime.now();
        String key=RedisConstants.USER_SIGN+now.format(DateTimeFormatter.ofPattern(":yyyyMM"));;
        int dayOfMonth = now.getDayOfMonth();
        //查询:获取本月截止今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD sign:5:202203 GET u14 0
        List<Long> result = template.opsForValue()
                .bitField(key, BitFieldSubCommands
                        .create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        if(result==null||result.isEmpty()){
            return Result.success(0);
        }
        Long num=result.get(0);
        if(num==null||num==0){
            return Result.success(0);
        }
        int ans=0;
        while ((num & 1) != 0) {
            ans++;
            num >>>= 1;
        }

        return Result.success(ans);
    }

    @Override
    public Result sign() {
        //获取用户信息
        Long userId = UserHolder.getUser().getId();
        //获取日期
        LocalDateTime now = LocalDateTime.now();
        String key=RedisConstants.USER_SIGN+now.format(DateTimeFormatter.ofPattern(":yyyyMM"));;
        int day = now.getDayOfMonth();
        //存储到redis
        template.opsForValue().setBit(key,day-1,true);
        return Result.success();
    }

    private String getToken(String phone) {
        return UUID.randomUUID().toString(true);
    }
}
