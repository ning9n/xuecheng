package com.dp.util;

import com.dp.dto.UserDTO;
import com.dp.entity.UserInfo;

public class UserHolder {
    private static final ThreadLocal<UserDTO> tl=new ThreadLocal<>();
    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
