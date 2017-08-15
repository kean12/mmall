package com.mmall.controller.backend;


import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manger/category")
public class CategoryManagerController {

    public ServerResponse addCatory(HttpSession session, String categoryName, @RequestParam(value = "parenId", defaultValue = "0") int parenId) {
        User user =(User) session.getAttribute(Const.CURRENT_USER);

  if (user ==null){
      return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录")
  }
  //检验一下是否是管理员

    }


}
