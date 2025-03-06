package com.dp.util;

import cn.hutool.core.bean.BeanUtil;
import com.dp.dto.UserDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate template;

    public RefreshTokenInterceptor(StringRedisTemplate template) {
        this.template=template;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        //获取token
        String token = request.getParameter(RedisConstants.USER_LOGIN_TOKEN);
        if(token==null){
            return true;
        }
        //校验token
        Map<Object, Object> map = template.opsForHash().entries(RedisConstants.USER_LOGIN_TOKEN + token);
        if(map.isEmpty()){
            return true;
        }
        //保存用户信息到UserHolder，刷新token
        UserDTO userDTO = BeanUtil.fillBeanWithMap(map, new UserDTO(), false);
        UserHolder.saveUser(userDTO);
        return true;

    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }

}
