package com.bjpowernode.crm.settings.handler;

import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器方法,拦截用户请求
public class LoginInterceptor implements HandlerInterceptor {


    //预处理方法, 在控制器方法之前执行, 用户的请求首先到达此方法
    //可以验证用户是否有权限访问某个连接地址(url)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute("sessionUser");
        if (user==null){
            //重定向回登入页
            response.sendRedirect(request.getContextPath());
            return false;
        }
        return true;
    }


    /*
            后处理方法
            参数:
              Object handler: 被拦截的处理器对象MyController
              ModelAndView modelAndView:处理器方法的返回值
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /*
            最后处理的方法
            参数:
              Object handler: 被拦截的处理器对象
              Exception ex: 程序中发生的异常
            特点:
              在请求处理完成后执行的
              一般做资源回收工作的, 程序请求过程中创建的对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}












