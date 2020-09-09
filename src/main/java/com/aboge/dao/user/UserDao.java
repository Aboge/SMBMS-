package com.aboge.dao.user;/*
 * @author aboge
 * @date 2020/9/7
 * */

import com.aboge.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

//用户相关操作方法的持久化
public interface UserDao {
    //得到要登陆的用户
    public User getLoginUser(Connection connection,String userCode) throws SQLException;

}
