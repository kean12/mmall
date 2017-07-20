package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Keane on 6/25/17.
 */
@Service("iUserService") public class UserServiceImpl implements IUserService {
    @Autowired private UserMapper userMapper;


    @Override public ServerResponse<User> login(String username, String password) {
        int resutlCount = userMapper.checkUsername(username);
        if (resutlCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //todo 密码登陆MD5

        String md5Password = MD5Util.MD5EncodeUtf8(password);


        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户名不能为空");
        }

        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess("登陆成功", user);
    }


    public ServerResponse<String> register(User user) {


        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }



        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {

            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");

    }

    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户已经存在");
                }
            }

            if (Const.EMAIL.equals(type)) {

                if (Const.EMAIL.equals(type)) {
                    int resultCount = userMapper.checkEmail(str);
                    if (resultCount > 0) {
                        return ServerResponse.createByErrorMessage("用户已经存在");
                    }
                }


            }

        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }


    public ServerResponse selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            //用户名不存在
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }


    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            //说明问题及问题答案是这个用户的，并且是正确的。
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);

        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
        // return null;
    }

    public ServerResponse<String> forgetRestPassword(String username, String passwordNew,
        String forgetToken) {
        if (org.apache.commons.lang3.StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误， token 需要传递");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            //用户名不存在
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);

        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {

            return ServerResponse.createByErrorMessage("token 无效或过期");
        }
        if (StringUtils.equals(forgetToken, token)) {
            String md5password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5password);

            if (rowCount > 0) {
                return ServerResponse.createByErrorMessage("修改密码成功");
            }
        } else {
            return ServerResponse.createByErrorMessage("token 错误，请宠幸获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }


}

