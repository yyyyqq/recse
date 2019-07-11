package com.recse4cloud.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginedUserInfo {

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    public static User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User) {
            return (User) auth.getPrincipal();
        }
        return new User();
    }

    /**
     * 获取当前登录用户编号
     *
     * @return
     */
    public static String getUserNo() {
        return getUser().getUserNo();
    }
}
