package com.yuyongsong.ssm.controller;

import com.yuyongsong.ssm.pojo.User;
import com.yuyongsong.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public ModelAndView registerDo(User user){

        ModelAndView mav = new ModelAndView();
        try{
            userService.insertUser(user);
            mav.addObject("success","/login");
            mav.addObject("msg","注册成功、前往登录");

        }catch (DataAccessException e){
            mav.addObject("crash","用户名已被注册");

        }

        mav.setViewName("register");
        return mav;
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public ModelAndView loginDo(User user){
        User user1 = userService.selectUser(user);
        ModelAndView mav = new ModelAndView();
        if(user1==null){
            System.out.println("账号或者密码错误");
            mav.addObject("svm","账号或者密码错误");
            System.out.println("账号或者密码错误");
            mav.setViewName("login");
        }else {
            mav.setViewName("index");
        }

        return mav;
    }
}
