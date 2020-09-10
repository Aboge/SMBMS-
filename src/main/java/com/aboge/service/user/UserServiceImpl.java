package com.aboge.service.user;/*
 * @author aboge
 * @date 2020/9/7
 * */

import com.aboge.dao.BaseDao;
import com.aboge.dao.user.UserDao;
import com.aboge.dao.user.UserDaoImpl;
import com.aboge.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    //业务层都会调用dao层中的UserDao接口方法，所以我们要实例化UserDao接口对象，并调用其方法
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    public User login(String userCode, String userPassword) {
        Connection connection = null;
        User user = null;


        //System.out.println(userCode+ "----" +userPassword);

        try {
            //连接数据库,调用dao层中的BaseDao实体类的getConnection方法，来操作数据库
//            BaseDao baseDao = new BaseDao();
            connection = BaseDao.getConnection();

//            System.out.println(connection);

            //获取登陆用户信息，调用userDao的getLoginUser方法来获取user返回值结果
            user = userDao.getLoginUser(connection, userCode);

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

//        System.out.println(user.getAddress());

        return user;
    }

    //测试数据库连接，及userDao.getLoginUser方法调用是否成功
    @Test
    public void test(){
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "12345678910");
        System.out.println(admin.getUserPassword());
        System.out.println(admin.getPhone());
    }

}
