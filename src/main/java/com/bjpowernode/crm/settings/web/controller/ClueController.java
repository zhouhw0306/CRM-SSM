package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.Clue;
import com.bjpowernode.crm.settings.domain.Tran;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.ClueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClueController {

    @Resource
    private UserService userService;

    @Resource
    private ClueService clueService;

    @RequestMapping("/workbench/clue/getUserList.do")
    public @ResponseBody List getUserList(){

        List<User> userList = userService.selectUserList();
        return userList;
    }

    //线索的添加方法
    @RequestMapping("/workbench/clue/save.do")
    public @ResponseBody boolean save(Clue clue, HttpServletRequest request){

        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setCreateBy(((User)request.getSession().getAttribute("sessionUser")).getName());

        boolean flag = clueService.save(clue);
        return flag;
    }

    //分页的方法
    @RequestMapping("/workbench/clue/pageList.do")
    public @ResponseBody PaginationVO pageList(Integer pageNo, Integer pageSize){

        //计算出略过的记录数
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        PaginationVO<Clue> vo = clueService.pageList(map);

        return vo;
    }

    //进入到跳转的详情页
    @RequestMapping("/workbench/clue/detail.do")
    public ModelAndView detail(String id){

        Clue c = clueService.detail(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("c",c);
        mv.setViewName("workbench/clue/detail");
        return mv;

    }

    //根据线索id获得所有关联的市场活动
    @RequestMapping("/workbench/clue/getActivityListByClueId.do")
    public @ResponseBody List getActivityListByClueId(String clueId){

        List<Activity> list = clueService.getActivityListByClueId(clueId);
        return list;

    }

    //线索关联市场活动删除的方法
    @RequestMapping("/workbench/clue/unbund.do")
    public @ResponseBody boolean unbund(String id){

        boolean flag = clueService.unbund(id);

        return flag;
    }

    //查询市场活动列表(根据名称模糊查+排除掉已经关联指定线索的列表)
    @RequestMapping("/workbench/clue/getActivityListByNameAndNotByClueId.do")
    public @ResponseBody List getActivityListByNameAndNotByClueId(String aname,String clueId){

        List<Activity> list = clueService.getActivityListByNameAndNotByClueId(aname,clueId);
        return list;
    }


    //执行关联市场活动的操作
    @RequestMapping("/workbench/clue/bund.do")
    public @ResponseBody boolean bund(HttpServletRequest request){

        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");

        boolean flag = clueService.bund(cid,aids);
        return flag;
    }


    @RequestMapping("/workbench/clue/convert.do")
    public ModelAndView convert(String id,String fullname,String appellation,String company,String owner){

        ModelAndView mv = new ModelAndView();
        mv.addObject("id",id);
        mv.addObject("fullname",fullname);
        mv.addObject("appellation",appellation);
        mv.addObject("company",company);
        mv.addObject("owner",owner);
        mv.setViewName("workbench/clue/convert");
        return mv;
    }


    //查询市场活动列表(根据名称模糊查)
    @RequestMapping("/workbench/clue/getActivityListByName.do")
    public @ResponseBody List getActivityListByName(String aname){

        List<Activity> list = clueService.getActivityListByName(aname);
        return list;
    }

//    //执行线索转换的操作，发出传统请求，请求结束后，最终响应回线索列表页
//    @RequestMapping("/workbench/clue/convertzh.do")
//    public ModelAndView convert(String clueId, String flag, String money, String name, String expectedDate, String stage, String activityId, HttpSession session){
//
//        Tran t = null;
//
//        String createBy = "";
//
//        //如果需要创建交易
//        if ("a".equals(flag)){
//
//            t = new Tran();
//
//            t.setId(UUIDUtil.getUUID());
//            t.setMoney(money);
//            t.setName(name);
//            t.setExpectedDate(expectedDate);
//            t.setStage(stage);
//            t.setActivityId(activityId);
//            createBy = ((User)session.getAttribute("sessionUser")).getName();
//            t.setCreateBy(createBy);
//            t.setCreateTime(DateTimeUtil.getSysTime());
//        }
//
//        boolean flag1 = clueService.convert(clueId,t,createBy);
//
//        ModelAndView mv = new ModelAndView();
//
//        if (flag1){
//            //response.sendRedirect("/WEB-INF/pages/workbench/clue/index.jsp");
//            mv.setViewName("workbench/clue/index");
//        }
//        return mv;
//
//    }

    //执行线索转换的操作，发出传统请求，请求结束后，最终响应回线索列表页
    @RequestMapping("/workbench/clue/convertzh.do")
    private ModelAndView convert(String flag, String clueId,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();

        Tran tran = null;
        String createBy = ((User) request.getSession().getAttribute("sessionUser")).getName();
        //接收是否需要创建的标记
        if ("a".equals(flag)){
            tran = new Tran();
            //接收交易表单中的参数
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setMoney(request.getParameter("money"));
            tran.setName(request.getParameter("name"));
            tran.setExpectedDate(request.getParameter("expectedDate"));
            tran.setStage(request.getParameter("stage"));
            tran.setActivityId(request.getParameter("activityId"));
            tran.setCreateBy(createBy);
        }
        boolean returnFlag = clueService.convert(clueId,tran,createBy);
        System.out.println("======="+returnFlag+"=======");
        if (returnFlag){
            mv.setViewName("workbench/clue/index");
        }
        return mv;

    }

}