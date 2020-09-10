package com.aboge.dao;/*
 * @author aboge
 * @date 2020/5/21
 * */

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

//基础操作方法
//操作数据库的公共方法
//读取db.properties文件来读取数据库的登陆信息
public class BaseDao {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块，类加载的时候就初始化了
    static {
        try {
//            // 获得资源包
//            ResourceBundle rb = ResourceBundle.getBundle("db.properties");
//            // 通过资源包拿到所有的key
//            Enumeration<String> allKey = rb.getKeys();
//            // 遍历key 得到 value
//            while (allKey.hasMoreElements()) {
//                String key = allKey.nextElement();
//                String value = (String) rb.getString(key);
//                switch (key) {
//                    case "url":
//                        url = value;
//                        break;
//                    case "driver":
//                        driver = value;
//                        break;
//                    case "username":
//                        username = value;
//                        break;
//                    case "password":
//                        password = value;
//                        break;
//                }
//            }

            //getClassLoader():加载类加载器 之后返回一个类加载器

            //InputStream in = ClassLoader.getSystemResourceAsStream("db");
            //InputStream in = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");

//            Properties p = new Properties();
//            p.load(in);
//            url = p.getProperty("url");
//            driver = p.getProperty("driver");
//            username = p.getProperty("username");
//            password = p.getProperty("password");

            ResourceBundle in = ResourceBundle.getBundle("db");
            url = in.getString("url");
            driver = in.getString("driver");
            username = in.getString("username");
            password = in.getString("password");

            System.out.println("url的值是："+url);
            System.out.println("driver的值为："+driver);


        }catch (Exception e){
//            e.printStackTrace();
            //因为现在是要连接数据库，连接过程出现了异常就连接不上，所以直接抛出来一个错误Error
            throw new ExceptionInInitializerError();
        }
    }


    //获取数据库的连接
    public static Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(connection);

        return connection;
    }

    //编写查询公共类(方法重载)
    public static ResultSet execute(Connection connection,String sql,Object[] params,ResultSet resultSet,PreparedStatement preparedStatement) throws SQLException {
        //预编译的sql，在后面直接执行就可以了
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            //setObject，占位符从1开始，但是我们的数组是从0开始的，所以需要+1
            preparedStatement.setObject(i+1,params[i]);
        }

        resultSet = preparedStatement.executeQuery();

        return resultSet;

    }

    //编写增删改公共方法
    public static ResultSet excute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params, ResultSet resultSet) throws SQLException {
        //预编译的sql，在后面直接执行就可以了
        preparedStatement = connection.prepareStatement(sql);

        //要查询的参数个数未知，所以设置一个参数数组：Object[] params
        for (int i = 0; i < params.length; i++) {
            //setObject,占位符从1开始，但是我们的数组是从0开始！
            preparedStatement.setObject(i+1,params[i]);
        }

        resultSet = preparedStatement.executeQuery(); //跟新，用于执行sql语句，并返回执行结果
        return resultSet;
    }

    //释放资源
    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        //设置flag默认值为true
        boolean flag = true;


        if (resultSet != null){
            try {
                resultSet.close();
                //GC回收
                resultSet = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                //如果释放资源失败，flag变成false
                flag = false;
            }
        }

        if (connection != null){
            try {
                connection.close();
                //GC回收
                connection = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                //如果释放资源失败，flag变成false
                flag = false;
            }
        }
        if (preparedStatement != null){
            try {
                preparedStatement.close();
                //GC回收
                preparedStatement = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                //如果释放资源失败，flag变成false
                flag = false;
            }
        }

        return flag;
    }

}
