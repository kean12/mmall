package com.mmall.common;

/**
 * Created by Keane on 6/27/17.
 */
public class Const {
    public static final String CURRENT_USER="currentUser";
    public static final String EMAIL="eamil";
    public static final String USERNAME="username";
    public interface Role {
        int ROLE_CUSTOMER=0;//普通用户
        int ROLE_ADMIN=1;//管理员
    }
}
