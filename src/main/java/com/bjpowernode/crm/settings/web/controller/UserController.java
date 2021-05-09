package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index.do")
    public String index(){
        return "index";
    }

    @RequestMapping("/settings.toLogin.do")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/shichang.do")
    public String shichang(){
        return "workbench/activity/index";
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){

        Map<String,Object> map = new HashMap<>();
        String loginPwd1 = MD5Util.MD5Lower(loginPwd);//将密码使用MD5加密
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd1);
        User user = userService.queryUserByLoginActAndPwd(map);

        Map<String,Object> retMap = new HashMap<>();

        if (user == null){
            //账号或密码错误
            retMap.put("code",0);
            retMap.put("message","账号或密码错误");
        }else {
            //进一步判断账号是否过期, 状态是否被锁定, ip是否被允许
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowStr = simpleDateFormat.format(new Date());
            if (nowStr.compareTo(user.getExpireTime())>0){
                //账号过期
                retMap.put("code",0);
                retMap.put("message","账号已过期");
            }else if("0".equals(user.getLockState())) {
                //账号状态锁定
                retMap.put("code",0);
                retMap.put("message","账号状态锁定");
            }else if((!user.getAllowIps().contains(request.getRemoteAddr())) && !loginAct.equals("ww")){
                //ip受限
                retMap.put("code",0);
                retMap.put("message","ip受限");
            }else{
                //登入成功
                retMap.put("code",1);
                session.setAttribute("sessionUser",user);
                System.out.println("登录ip: "+request.getRemoteAddr());
                // 如要要记住密码
                if ("true".equals(isRemPwd)){

                    Cookie c1 = new Cookie("loginAct",loginAct);
                    c1.setMaxAge(60*60*24*10);
                    Cookie c2 = new Cookie("loginPwd",loginPwd);
                    c2.setMaxAge(60*60*24*10);
                    response.addCookie(c1);
                    response.addCookie(c2);
                }else {
                    Cookie c1 = new Cookie("loginAct",loginAct);
                    c1.setMaxAge(0);
                    Cookie c2 = new Cookie("loginPwd",loginPwd);
                    c2.setMaxAge(0);
                    response.addCookie(c1);
                    response.addCookie(c2);
                }


            }

        }
        return retMap;
    }

    @RequestMapping("/workbench/index.do")
    public String shouye(){
        return "workbench/index";
    }


    @RequestMapping("/workbench/main/index.do")
    public String gzqu(){
        return "workbench/main/index";
    }


    //退出登入
    @RequestMapping("/pages/login.do")
    public String exit(HttpSession session){

        session.removeAttribute("sessionUser");
        return "index";
    }

}
