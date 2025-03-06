package com.dp.config;

import com.dp.util.LoginInterceptor;
import com.dp.util.RefreshTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final StringRedisTemplate template;

    /**
     * 拦截器包括刷新token拦截器和登录拦截器
     * 刷新token拦截器只负责刷新token及保存用户信息到UserHolder
     * 登录拦截器负责校验UserHolder
     *
     * @param registry registry
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //token刷新
        registry.addInterceptor(new RefreshTokenInterceptor(template))
                .addPathPatterns("/**")
                .order(0);
        //登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/shop/**",
                        "/voucher/**",
                        "/shop-type/**",
                        "/upload/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/login"
                ).order(1);
    }
}
