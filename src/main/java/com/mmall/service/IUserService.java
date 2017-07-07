package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;


/**
 * Created by Keane on 6/25/17.
 */
public interface IUserService {


    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str, String type);
    ServerResponse selectQuestion (String username);

    ServerResponse<String> checkAnswer (String username , String question, String answer);

}
