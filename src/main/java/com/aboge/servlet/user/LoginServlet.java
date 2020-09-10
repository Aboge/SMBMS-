package com.aboge.servlet.user;/*
 * @author aboge
 * @date 2020/9/7
 * */

import com.aboge.pojo.User;
import com.aboge.service.user.UserService;
import com.aboge.service.user.UserServiceImpl;
import com.aboge.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet{

    //Servlet：控制层，调用业务层Service代码


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("LoginServelet--start...");
        //获取前端输入的用户账号及密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");


        //和数据库中用户对应的密码进行比对，需要调用业务层UserService
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode,userPassword); //根据用户的账户信息已经把用户在数据库中的信息查询出来了



        if(user != null){ //查有此人可以登陆
            //将用户的信息存放在session中，保持登陆状态
            req.getSession().setAttribute(Constants.USER_SESSION,user);

            //登陆成功后跳转到内部主页
            resp.sendRedirect("jsp/frame.jsp");
        }else {      //查无此人，无法登陆
            //转发回登陆主页，并提示登陆信息（用户名或者密码）错误
            req.setAttribute("error","用户名或者登陆密码错误，请重新登陆");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
