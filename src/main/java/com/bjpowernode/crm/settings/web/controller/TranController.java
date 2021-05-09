package com.bjpowernode.crm.settings.web.controller;


import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TranController {

    @Resource
    private UserService userService;

    //进入到跳转交易添加页的操作
    @RequestMapping("workbench/transaction/save.do")
    public ModelAndView add(){

        List<User> uList = userService.selectUserList();
        ModelAndView mv = new ModelAndView();
        mv.addObject("uList",uList);
        mv.setViewName("workbench/transaction/save");
        return mv;

    }


}
