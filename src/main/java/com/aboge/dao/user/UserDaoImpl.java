package com.aboge.dao.user;/*
 * @author aboge
 * @date 2020/9/7
 * */

import com.aboge.dao.BaseDao;
import com.aboge.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    //得到要登陆的用户
    public User getLoginUser(Connection connection, String userCode) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        User user = null;


        //先成功链接数据库后在执行sql查询语句
        if (connection != null) {
            String sql = "select * from smbms_user where userCode = ?";
            Object[] params = {userCode};


            //执行sql查询，返回结果集rs,是链表形式
            rs = BaseDao.excute(connection, preparedStatement, sql, params, rs);

            //在结果集未空时,查询出改用户的相关信息，并赋值存储封装在User类型的对象user中，并返回
            if (rs.next()) {

                //新建user对象
                user = new User();

                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.getCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getDate("modifyDate"));
            }

            //在查询完毕后，关闭sql，节约资源
            BaseDao.closeResource(null, preparedStatement, rs);

        }

        return user;
    }
}
