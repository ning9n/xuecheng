package com.dp.util;

import java.util.concurrent.TimeUnit;

public class RedisConstants {
    public static final String CODE="code";
    public static final Integer CODE_TTL=30;
    public static final TimeUnit CODE_TIMEUNIT=TimeUnit.MINUTES;
    public static final String USER_LOGIN_TOKEN="user_login_token";
    public static final Integer USER_LOGIN_TOKEN_TTL=30;
    public static final TimeUnit USER_LOGIN_TOKEN_TIMEUNIT=TimeUnit.MINUTES;

    public static final String USER_SIGN="user_sign";
}
