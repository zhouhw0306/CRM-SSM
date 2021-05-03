package com.bjpowernode.crm.settings.handler;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

//监听器
public class SysInitListener implements ServletContextListener {

    /*
        该方法是用来监听上下文域对象的方法, 当服务器启动, 上下文域对象创建对象创建完毕后, 马上执行该方法

        event: 该参数能够取得监听的对象
                 监听的是什么对象, 就可以通过该参数能取得什么对象
                 例如我们现在监听的上下文域对象, 通过该参数就可以取得上下文域对象
     */


    public void contextInitialized(ServletContextEvent event) {

        ServletContext application = event.getServletContext();


        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        DicService dicService = (DicService) ctx.getBean("DicService");

        Map<String,List<DicValue>> map = dicService.getAll();

        //将map解析为上下文域对象中保存的键值对
        Set<String> set = map.keySet();
        for (String key : set){
            application.setAttribute(key,map.get(key));
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }


}
