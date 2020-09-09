package com.aboge.service.user;/*
 * @author aboge
 * @date 2020/9/7
 * */

import com.aboge.pojo.User;

//
public interface UserService {
    //用户登陆,获取用户
    public User login(String userCode,String userPassword);
}
